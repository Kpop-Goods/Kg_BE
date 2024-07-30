package hello.kpop.user.controller;

import hello.kpop.user.User;
import hello.kpop.user.dto.UserRequestDto;
import hello.kpop.user.dto.UserResponseDto;
import hello.kpop.user.dto.UserSuccessResponseDto;
import hello.kpop.user.handler.CustomResponse;
import hello.kpop.user.handler.exception.DuplicateEmailException;
import hello.kpop.user.handler.exception.DuplicatePasswordException;
import hello.kpop.user.handler.exception.InvalidTokenException;
import hello.kpop.user.handler.exception.UserNotFoundException;
import hello.kpop.user.repository.UserRepository;
import hello.kpop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 유효성 검사 에러 처리
    private ResponseEntity<CustomResponse> handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            CustomResponse customResponse = new CustomResponse();
            customResponse.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            customResponse.setMessage("유효성 검사 실패");
            customResponse.setData(errors);
            return ResponseEntity.unprocessableEntity().body(customResponse);
        }
        return null;
    }

    // 예외 처리 및 응답 생성
    private ResponseEntity<CustomResponse> handleResponse(Exception e, HttpStatus errorStatus, String successMessage, Object data) {
        CustomResponse customResponse = new CustomResponse();
        if (e != null) {  // 예외가 발생한 경우
            if (e instanceof DuplicateEmailException) {
                customResponse.setStatusCode(400);
                customResponse.setMessage(e.getMessage());
            } else {
                customResponse.setStatusCode(errorStatus.value());
                customResponse.setMessage(e.getMessage());
            }
        } else {  // 예외가 발생하지 않은 경우
            customResponse.setStatusCode(errorStatus.value());
            customResponse.setMessage(successMessage);
        }
        if (data != null) {
            customResponse.setData(data);
        }
        return ResponseEntity.status(errorStatus).body(customResponse);
    }

    // 회원가입 API
    @PostMapping("/userSignUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) throws Exception {
        ResponseEntity<CustomResponse> validationResponse = handleValidationErrors(bindingResult);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            userService.signUp(userRequestDto);
            return ResponseEntity.ok().body(new CustomResponse(HttpStatus.OK.value(), "회원가입 성공", null));
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(400, e.getMessage(), null));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, e.getMessage(), null));
        }
    }

    // 유저 회원정보 수정 API
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        ResponseEntity<CustomResponse> validationResponse = handleValidationErrors(bindingResult);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            return ResponseEntity.ok().body(new CustomResponse(HttpStatus.OK.value(), "회원정보 수정 성공", userService.updateUser(authentication, userRequestDto)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "회원정보 수정 실패", null));
        }
    }


    // 회원정보 조회
    @PostMapping("/searchUser")
    public ResponseEntity<?> searchUser(Authentication authentication) {
        try {
            return ResponseEntity.ok().body(new CustomResponse(HttpStatus.OK.value(), "회원정보 조회 성공", userService.searchUser(authentication)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "회원정보 조회 실패", null));
        }
    }

    // 유저 회원 탈퇴 API
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        try {
            userService.deleteUser(authentication);
            return ResponseEntity.ok().body(new CustomResponse(HttpStatus.OK.value(), "회원 탈퇴 성공", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "회원 탈퇴 실패", null));
        }
    }

    // 로그아웃 API
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        try {
            userService.logout(authentication);
            return ResponseEntity.ok().body(new CustomResponse(HttpStatus.OK.value(), "로그아웃 성공", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse(500, "로그아웃 실패", null));
        }
    }

    // 비밀번호 재설정 확인 API
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");

        try {
            userService.resetPassword(email, token, newPassword);
            return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        } catch (DuplicatePasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("새로운 비밀번호는 이전 비밀번호와 달라야 합니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 재설정에 실패했습니다. " + e.getMessage());
        }
    }

}
