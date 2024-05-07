package hello.kpop.follow;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/v1/follow")
@RestController
@RequiredArgsConstructor
@Tag(name = "Follow", description = "Follow service for user, idol/artist, item/goods, meet/socialing, place")
public class FollowController {

    private final FollowService followService;

	private ResponseEntity<FollowResponse> responseIdList(List<Long> idList, int size) {
		return responseIdList(idList, size, false);
	}

	private ResponseEntity<FollowResponse> responseIdList(List<Long> idList, int size, boolean override) {
		FollowResponse response;
		HttpStatus httpStatus;

		int totalSize = idList.size();
		if (totalSize > 0 || override) {
			// found ids
			while (idList.size() > size) // reduce size of list as api param.
				idList.remove(idList.size() -1);

			httpStatus = HttpStatus.OK;
			response = FollowResponse.builder()
				.success(true)
				.statusCode(httpStatus.value())
				.message("조회 성공")
				.totalSize(totalSize)
				.IDs(idList)
				.responseCount(idList.size()).build();
		} else {
			// found nothing
			httpStatus = HttpStatus.OK; // for returning response body
			response = FollowResponse.builder()
				.success(false)
				.statusCode(httpStatus.value())
				.message("조회된 내용이 없습니다.")
				.totalSize(0)
				.IDs(Collections.emptyList())
				.responseCount(0).build();
		}

		return new ResponseEntity<>(response, httpStatus);
	}

	///////////////////////////////////////////////////////////////////////////
	// USER

	@PostMapping("/users/{userId}/users/{followId}") // Create
	@Operation(summary = "Set user follows a user", description = "Create a follow of userId to followId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> CreateUserFollowUser(
		@Parameter(description = "User ID of follow/following")
		@PathVariable("userId") Long userId,

		@Parameter(description = "follow ID for follower/followed")
		@PathVariable("followId") Long followId) {

		List<Long> idList = new ArrayList<>();

		if (followService.followUser(userId, followId) != true		||	// create relationship
			followService.isUserFollowUser(userId, followId) != true) {	// check relationship

			return responseIdList(idList, 0);
		}

		idList.add(followId); // build response
		return responseIdList(idList, 1);
	}

	@GetMapping("/users/{userId}/users") // Read
	@Operation(summary = "Get following user list", description = "Read user's following user list & size")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readUserFollowUserList(
		@Parameter(description = "User ID of reading following list of users")
		@PathVariable("userId") Long userId,

		@Parameter(name = "size", description = "max size of reading user list")
		@RequestParam Integer size) {

		return responseIdList(followService.getUserFollowUserList(userId), size);
	}

	@DeleteMapping("/users/{userId}/users/{followId}") // Delete
	@Operation(summary = "Set user unfollows a user", description = "Delete a follow of userId to followId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> DeleteUserFollowUser(
		@Parameter(description = "User ID of unfollow/unfollowing")
		@PathVariable("userId") Long userId,

		@Parameter(description = "follow ID for unfollower/unfollowed")
		@PathVariable("followId") Long followId) {

		if (followService.unfollowUser(userId, followId) != true	||	// create relationship
			followService.isUserFollowUser(userId, followId) != false) {// check relationship

			// return failure;false by empty list
			return responseIdList(Collections.emptyList(), 0);
		}

		// return success;ture with total count and zero list, so you need an override
		return responseIdList(followService.getUserFollowUserList(userId), 0, true);
	}

	@GetMapping("/users/{userId}/users/{followId}") // Read
	@Operation(summary = "Get user is followed or not", description = "Return true/false for user following followId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<Boolean> isUserFollowUser(
		@Parameter(description = "User ID of reading following list of users")
		@PathVariable("userId") Long userId,

		@Parameter(description = "follow ID for checking user following her/him")
		@PathVariable("followId") Long followId) {

		if (followService.isUserFollowUser(userId, followId) != true)
			return ResponseEntity.ok(false);

		return ResponseEntity.ok(true);
	}

	@GetMapping("/users/{userId}/followers") // Read
	@Operation(summary = "Get user's follower user list", description = "Read user's follower user list & size")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readUserFollowerUserList(
		@Parameter(description = "User ID of reading follower list of users")
		@PathVariable("userId") Long userId,

		@Parameter(name = "size", description = "max size of reading user list")
		@RequestParam Integer size) {

		return responseIdList(followService.getUserFollowerUserList(userId), size);
	}

	@GetMapping("/users/{userId}/follower3") // Read
	@Operation(summary = "Get user's 3 follower user list", description = "Read user's three follower user list")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readUser3FollowerUserList(
		@Parameter(description = "User ID of reading follower list of users")
		@PathVariable("userId") Long userId) {

		return readUserFollowerUserList(userId, 3);
	}

	///////////////////////////////////////////////////////////////////////////
	// IDOL

	@PostMapping("/users/{userId}/idols/{idolId}") // Create
	@Operation(summary = "Set user follows an idol", description = "Create a follow of userId to idolId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> CreateUserFollowIdol(
		@Parameter(description = "User ID of follow/following")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Idol ID for follower/followed")
		@PathVariable("idolId") Long idolId) {

		List<Long> idList = new ArrayList<>();

		if (followService.followIdol(userId, idolId) != true		||	// create relationship
			followService.isUserFollowIdol(userId, idolId) != true) {	// check relationship

			return responseIdList(idList, 0);
		}

		idList.add(idolId); // build response
		return responseIdList(idList, 1);
	}

	@DeleteMapping("/users/{userId}/idols/{idolId}") // Delete
	@Operation(summary = "Set user unfollows an idol", description = "Delete a follow of userId to idolId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> DeleteUserFollowIdol(
		@Parameter(description = "User ID of unfollow/unfollowing")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Idol ID for unfollower/unfollowed")
		@PathVariable("idolId") Long idolId) {

		if (followService.unfollowIdol(userId, idolId) != true	||	// create relationship
			followService.isUserFollowIdol(userId, idolId) != false) {// check relationship

			// return failure;false by empty list
			return responseIdList(Collections.emptyList(), 0);
		}

		// return success;ture with total count and zero list, so you need an override
		return responseIdList(followService.getUserFollowIdolList(userId), 0, true);
	}

	@GetMapping("/users/{userId}/idols") // Read
	@Operation(summary = "Get following idol list", description = "Read user's following idol list & size")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readUserFollowIdolList(
		@Parameter(description = "User ID of reading following list of idols")
		@PathVariable("userId") Long userId,

		@Parameter(name = "size", description = "max size of reading idol list")
		@RequestParam Integer size) {

		return responseIdList(followService.getUserFollowIdolList(userId), size);
	}

	@GetMapping("/users/{userId}/idols/{idolId}") // Read
	@Operation(summary = "Get idol is followed or not", description = "Return true/false for user following idolId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<Boolean> isUserFollowIdol(
		@Parameter(description = "User ID of reading following list of idols")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Idol ID for checking user following idol")
		@PathVariable("idolId") Long idolId) {

		if (followService.isUserFollowIdol(userId, idolId) != true)
			return ResponseEntity.ok(false);

		return ResponseEntity.ok(true);
	}

	@GetMapping("/idols/{idolId}/followers") // Read
	@Operation(summary = "Get idol's follower user list", description = "Read idol's follower user list & size")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readIdolFollowerUserList(
		@Parameter(description = "Idol ID of reading follower list of users")
		@PathVariable("idolId") Long idolId,

		@Parameter(name = "size", description = "max size of reading user list")
		@RequestParam Integer size) {

		return responseIdList(followService.getIdolFollowerUserList(idolId), size);
	}

	@GetMapping("/idols/{idolId}/follower3") // Read
	@Operation(summary = "Get idol's 3 follower user list", description = "Read idol's three follower user list")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readIdol3FollowerUserList(
		@Parameter(description = "Idol ID of reading follower list of users")
		@PathVariable("idolId") Long idolId) {

		return readIdolFollowerUserList(idolId, 3);
	}

	///////////////////////////////////////////////////////////////////////////
	// ITEM

	@GetMapping("/users/{userId}/items") // Read
	@Operation(summary = "Get following item list", description = "Read user's following item list & size")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readUserFollowItemList(
		@Parameter(description = "User ID of reading following list of items")
		@PathVariable("userId") Long userId,

		@Parameter(name = "size", description = "max size of reading item list")
		@RequestParam Integer size) {

		return responseIdList(followService.getUserFollowItemList(userId), size);
	}

	@GetMapping("/users/{userId}/items/{itemId}") // Read
	@Operation(summary = "Get item is followed or not", description = "Return true/false for user following itemId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<Boolean> isUserFollowItem(
		@Parameter(description = "User ID of reading following list of items")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Item ID for checking user following item")
		@PathVariable("itemId") Long itemId) {

		if (followService.isUserFollowItem(userId, itemId) != true)
			return ResponseEntity.ok(false);

		return ResponseEntity.ok(true);
	}

	@PostMapping("/users/{userId}/items/{itemId}") // Create
	@Operation(summary = "Set user follows an item", description = "Create a follow of userId to itemId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> CreateUserFollowItem(
		@Parameter(description = "User ID of follow/following")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Item ID for follower/followed")
		@PathVariable("itemId") Long itemId) {

		List<Long> idList = new ArrayList<>();

		if (followService.followItem(userId, itemId) != true		||	// create relationship
			followService.isUserFollowItem(userId, itemId) != true) {	// check relationship

			return responseIdList(idList, 0);
		}

		idList.add(itemId); // build response
		return responseIdList(idList, 1);
	}

	@DeleteMapping("/users/{userId}/items/{itemId}") // Delete
	@Operation(summary = "Set user unfollows an item", description = "Delete a follow of userId to itemId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> DeleteUserFollowItem(
		@Parameter(description = "User ID of unfollow/unfollowing")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Item ID for unfollower/unfollowed")
		@PathVariable("itemId") Long itemId) {

		if (followService.unfollowItem(userId, itemId) != true	||	// create relationship
			followService.isUserFollowItem(userId, itemId) != false) {// check relationship

			// return failure;false by empty list
			return responseIdList(Collections.emptyList(), 0);
		}

		// return success;ture with total count and zero list, so you need an override
		return responseIdList(followService.getUserFollowItemList(userId), 0, true);
	}

	///////////////////////////////////////////////////////////////////////////
	// MEET

	@GetMapping("/users/{userId}/meets") // Read
	@Operation(summary = "Get following meet list", description = "Read user's following meet list & size")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readUserFollowMeetList(
		@Parameter(description = "User ID of reading following list of meets")
		@PathVariable("userId") Long userId,

		@Parameter(name = "size", description = "max size of reading meet list")
		@RequestParam Integer size) {

		return responseIdList(followService.getUserFollowMeetList(userId), size);
	}

	@GetMapping("/users/{userId}/meets/{meetId}") // Read
	@Operation(summary = "Get meet is followed or not", description = "Return true/false for user following meetId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<Boolean> isUserFollowMeet(
		@Parameter(description = "User ID of reading following list of meets")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Meet ID for checking user following meet")
		@PathVariable("meetId") Long meetId) {

		if (followService.isUserFollowMeet(userId, meetId) != true)
			return ResponseEntity.ok(false);

		return ResponseEntity.ok(true);
	}

	@PostMapping("/users/{userId}/meets/{meetId}") // Create
	@Operation(summary = "Set user follows a meet", description = "Create a follow of userId to meetId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> CreateUserFollowMeet(
		@Parameter(description = "User ID of follow/following")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Meet ID for follower/followed")
		@PathVariable("meetId") Long meetId) {

		List<Long> idList = new ArrayList<>();

		if (followService.followMeet(userId, meetId) != true		||	// create relationship
			followService.isUserFollowMeet(userId, meetId) != true) {	// check relationship

			return responseIdList(idList, 0);
		}

		idList.add(meetId); // build response
		return responseIdList(idList, 1);
	}

	@DeleteMapping("/users/{userId}/meets/{meetId}") // Delete
	@Operation(summary = "Set user unfollows a meet", description = "Delete a follow of userId to meetId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> DeleteUserFollowMeet(
		@Parameter(description = "User ID of unfollow/unfollowing")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Meet ID for unfollower/unfollowed")
		@PathVariable("meetId") Long meetId) {

		if (followService.unfollowMeet(userId, meetId) != true	||	// create relationship
			followService.isUserFollowMeet(userId, meetId) != false) {// check relationship

			// return failure;false by empty list
			return responseIdList(Collections.emptyList(), 0);
		}

		// return success;ture with total count and zero list, so you need an override
		return responseIdList(followService.getUserFollowMeetList(userId), 0, true);
	}

	///////////////////////////////////////////////////////////////////////////
	// PLACE

	@GetMapping("/users/{userId}/places") // Read
	@Operation(summary = "Get following place list", description = "Read user's following place list & size")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> readUserFollowPlaceList(
		@Parameter(description = "User ID of reading following list of places")
		@PathVariable("userId") Long userId,

		@Parameter(name = "size", description = "max size of reading place list")
		@RequestParam Integer size) {

		return responseIdList(followService.getUserFollowPlaceList(userId), size);
	}

	@GetMapping("/users/{userId}/places/{placeId}") // Read
	@Operation(summary = "Get place is followed or not", description = "Return true/false for user following placeId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<Boolean> isUserFollowPlace(
		@Parameter(description = "User ID of reading following list of places")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Place ID for checking user following place")
		@PathVariable("placeId") Long placeId) {

		if (followService.isUserFollowPlace(userId, placeId) != true)
			return ResponseEntity.ok(false);

		return ResponseEntity.ok(true);
	}

	@PostMapping("/users/{userId}/places/{placeId}") // Create
	@Operation(summary = "Set user follows a place", description = "Create a follow of userId to placeId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> CreateUserFollowPlace(
		@Parameter(description = "User ID of follow/following")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Place ID for follower/followed")
		@PathVariable("placeId") Long placeId) {

		List<Long> idList = new ArrayList<>();

		if (followService.followPlace(userId, placeId) != true		||	// create relationship
			followService.isUserFollowPlace(userId, placeId) != true) {	// check relationship

			return responseIdList(idList, 0);
		}

		idList.add(placeId); // build response
		return responseIdList(idList, 1);
	}

	@DeleteMapping("/users/{userId}/places/{placeId}") // Delete
	@Operation(summary = "Set user unfollows a place", description = "Delete a follow of userId to placeId")
	@ApiResponse(responseCode = "200", description = "OK")
	public ResponseEntity<FollowResponse> DeleteUserFollowPlace(
		@Parameter(description = "User ID of unfollow/unfollowing")
		@PathVariable("userId") Long userId,

		@Parameter(description = "Place ID for unfollower/unfollowed")
		@PathVariable("placeId") Long placeId) {

		if (followService.unfollowPlace(userId, placeId) != true	||	// create relationship
			followService.isUserFollowPlace(userId, placeId) != false) {// check relationship

			// return failure;false by empty list
			return responseIdList(Collections.emptyList(), 0);
		}

		// return success;ture with total count and zero list, so you need an override
		return responseIdList(followService.getUserFollowPlaceList(userId), 0, true);
	}
}
