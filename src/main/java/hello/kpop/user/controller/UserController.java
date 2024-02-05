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

    // 유효성 검사 에러 처리
    private ResponseEntity<CustomResponse> handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            CustomResponse customResponse = new CustomResponse();
            customResponse.setStatusCode(422);
            customResponse.setMessage("유효성 검사 실패");
            customResponse.setData(errors);
            return ResponseEntity.unprocessableEntity().body(customResponse);
        }
        return null;  // 유효성 검사 에러가 없는 경우, null 반환
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

    // 회원가입
    @PostMapping("/userSignUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        ResponseEntity<CustomResponse> validationResponse = handleValidationErrors(bindingResult);
        if (validationResponse != null) {
            return validationResponse;  // 유효성 검사 에러가 있는 경우, 해당 응답 반환
        }

        try {
            Optional<User> existingUserOptional = userRepository.findByUserEmail(userRequestDto.getUserEmail());
            if (existingUserOptional.isPresent()) {
                throw new DuplicateEmailException("중복된 이메일입니다.");
            }

            UserResponseDto responseDto = userService.signUp(userRequestDto);
            return ResponseEntity.ok(handleResponse(null, HttpStatus.OK, "회원가입 성공", responseDto).getBody());
        } catch (Exception e) {
            return ResponseEntity.ok(handleResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 실패", null).getBody());
        }
    }

    // 유저 회원정보 수정
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        ResponseEntity<CustomResponse> validationResponse = handleValidationErrors(bindingResult);
        if (validationResponse != null) {
            return validationResponse;  // 유효성 검사 에러가 있는 경우, 해당 응답 반환
        }

        try {
            UserResponseDto responseDto = userService.updateUser(id, userRequestDto);
            return ResponseEntity.ok(handleResponse(null, HttpStatus.OK, "회원정보 수정 성공", responseDto).getBody());
        } catch (Exception e) {
            return ResponseEntity.ok(handleResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "회원정보 수정 실패", null).getBody());
        }
    }

    // 유저 회원 탈퇴
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            UserSuccessResponseDto successResponseDto = userService.deleteUser(id);
            return ResponseEntity.ok(handleResponse(null, HttpStatus.OK, "회원 탈퇴 성공", successResponseDto).getBody());
        } catch (Exception e) {
            return ResponseEntity.ok(handleResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "회원 탈퇴 실패", null).getBody());
        }
    }

    // 로그아웃
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        try {
            userService.logout(authentication);
            return ResponseEntity.ok(handleResponse(null, HttpStatus.OK, "로그아웃 성공", null).getBody());
        } catch (Exception e) {
            return ResponseEntity.ok(handleResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "로그아웃 실패", null).getBody());
        }
    }
}