package hello.kpop.user.controller;

import hello.kpop.user.User;
import hello.kpop.user.dto.UserRequestDto;
import hello.kpop.user.dto.UserResponseDto;
import hello.kpop.user.dto.UserSuccessResponseDto;
import hello.kpop.user.handler.CustomResponse;
import hello.kpop.user.handler.exception.DuplicateEmailException;
import hello.kpop.user.repository.UserRepository;
import hello.kpop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/userSignUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            CustomResponse customResponse = new CustomResponse();
            customResponse.setStatusCode(422);
            customResponse.setMessage("유효성 검사 실패");
            customResponse.setData(errors);
            return ResponseEntity.unprocessableEntity().body(customResponse); // 422 Unprocessable Entity with custom response
        } else {
            try {
                Optional<User> existingUserOptional = userRepository.findByUserEmail(userRequestDto.getUserEmail());
                if (existingUserOptional.isPresent()) {
                    throw new DuplicateEmailException("중복된 이메일입니다.");
                }

                UserResponseDto responseDto = userService.signUp(userRequestDto);
                CustomResponse successResponse = new CustomResponse();
                successResponse.setStatusCode(200);
                successResponse.setMessage("회원가입 성공");
                successResponse.setData(responseDto);
                return ResponseEntity.ok(successResponse); // Successful signup with 200 OK and data
            } catch (DuplicateEmailException e) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setStatusCode(400);
                customResponse.setMessage(e.getMessage());
                return ResponseEntity.badRequest().body(customResponse); // Duplicate email error with 400 Bad Request
            } catch (Exception e) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setStatusCode(500);
                customResponse.setMessage("회원가입 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse); // Failure with 500 Internal Server Error
            }
        }
    }

    // 유저 회원정보 수정
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            CustomResponse customResponse = new CustomResponse();
            customResponse.setStatusCode(422);
            customResponse.setMessage("유효성 검사 실패");
            customResponse.setData(errors);
            return ResponseEntity.unprocessableEntity().body(customResponse); // 422 Unprocessable Entity with custom response
        } else {
            try {
                UserResponseDto responseDto = userService.updateUser(id, userRequestDto);
                CustomResponse successResponse = new CustomResponse();
                successResponse.setStatusCode(200);
                successResponse.setMessage("회원정보 수정 성공");
                successResponse.setData(responseDto);
                return ResponseEntity.ok(successResponse); // Successful update with 200 OK and data
            }
            catch (Exception e) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setStatusCode(400);
                customResponse.setMessage("회원정보 수정 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
            }
        }
    }

    // 유저 회원 탈퇴
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            UserSuccessResponseDto successResponseDto = userService.deleteUser(id);
            CustomResponse successResponse = new CustomResponse();
            successResponse.setStatusCode(200);
            successResponse.setMessage("회원 탈퇴 성공");
            successResponse.setData(successResponseDto);
            return ResponseEntity.ok(successResponse); // Successful deletion with 200 OK and data
        } catch (Exception e) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setStatusCode(400);
            customResponse.setMessage("회원 탈퇴 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
        }
    }


//    @GetMapping("/jwt-test")
//    public String jwtTest() {
//        return "jwtTest 요청 성공";
//    }


    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        try {
            userService.logout(authentication);
            CustomResponse successResponse = new CustomResponse();
            successResponse.setStatusCode(200);
            successResponse.setMessage("로그아웃 성공");
            return ResponseEntity.ok(successResponse); // 로그아웃 성공시 200 OK 반환
        } catch (Exception e) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setStatusCode(400);
            customResponse.setMessage("로그아웃 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse); // 실패시 400 오류 반환
        }
    }
}
