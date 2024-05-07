package hello.kpop.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FollowService {
	@Qualifier("follow.UserRepository")
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IdolRepository idolRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private MeetRepository meetRepository;
	@Qualifier("follow.PlaceRepository")
	@Autowired
	private PlaceRepository placeRepository;

	//// user
	public boolean addUser(Long userId, String name, String email) {
		User check = userRepository.findOneByUserId(userId);
		if (check != null) return false; // exist

		User user = new User(userId, name, email);
		User ret = userRepository.save(user);
		if (ret == null) return false;

		return true;
	}

	public boolean deleteUser(Long userId) {
		User user = userRepository.findOneByUserId(userId);
		if (user == null) return false; // not exist

		userRepository.delete(user);
		return true;
	}

	public boolean modifyUser(Long userId, String name) {
		User user = userRepository.findOneByUserId(userId);
		if (user == null) return false; // not exist

		user.setName(name);
		//	user.setEmail(email); // email can't be changed
		User ret = userRepository.save(user);
		if (ret == null) return false;

		return true;
	}

	// follow/following
	public boolean followUser(Long userId, Long followId) {
		User user   = userRepository.findOneByUserId(userId);
		User follow = userRepository.findOneByUserId(followId);
		if (userId == null || follow   == null) return false;

		user.followWith(follow);
		userRepository.save(user);
		return true;
	}

	public boolean unfollowUser(Long userId, Long followId) {
		User user   = userRepository.findOneByUserId(userId);
		User follow = userRepository.findOneByUserId(followId);
		if (userId == null || follow   == null) return false;

		userRepository.unfollowUser(userId, followId);
		return true;
	}

	public int getUserFollowCount(Long userId) {
		User user = userRepository.findOneByUserId(userId);
		if (user == null) return 0;

		return userRepository.getFollowUserCount(userId);
	}

	public List<Long> getUserFollowUserList(Long userId) {
		List<Long> userIds = new ArrayList<>();

		User user = userRepository.findOneByUserId(userId);
		if (user == null) return userIds;

		return userRepository.getFollowUserList(userId);
	}

	public Boolean isUserFollowUser(Long userId, Long followId) {
		User user   = userRepository.findOneByUserId(userId);
		User follow = userRepository.findOneByUserId(followId);
		if (user == null || follow == null) return false;

		if (userRepository.isFollowUser(userId, followId) <= 0) {
			return false;
		}

		return true;
	}

	// follower/followed
	public int getUserFollowerCount(Long userId) {
		User user = userRepository.findOneByUserId(userId);
		if (user == null) return 0;

		return userRepository.getFollowerUserCount(userId);
	}

	public List<Long> getUserFollowerUserList(Long userId) {
		List<Long> userIds = new ArrayList<>();

		User user = userRepository.findOneByUserId(userId);
		if (user == null) return userIds;

		return userRepository.getFollowerUserList(userId);
	}

	//// idol
	public boolean addIdol(Long idolId, String name, Boolean unit, Boolean gender) {
		Idol check = idolRepository.findOneByIdolId(idolId);
		if (check != null) return false; // exist

		Idol idol = new Idol(idolId, name, unit, gender);
		Idol ret = idolRepository.save(idol);
		if (ret == null) return false;

		return true;
	}

	public boolean deleteIdol(Long idolId) {
		Idol idol = idolRepository.findByIdolId(idolId);
		if (idol == null) return false; // not exist

		idolRepository.delete(idol);
		return true;
	}

	public boolean modifyIdol(Long idolId, String name, Boolean unit, Boolean gender) {
		Idol idol = idolRepository.findByIdolId(idolId);
		if (idol == null) return false; // not exist

		idol.setName(name);
		idol.setUnit(unit);
		idol.setGender(gender);
		Idol ret = idolRepository.save(idol);
		if (ret == null) return false;

		return true;
	}

	public boolean addArtist(Long idolId, String name, Boolean unit, Boolean gender) {
		return addIdol(idolId, name, unit, gender);
	}
	public boolean deleteArtist(Long idolId) {
		return deleteIdol(idolId);
	}
	public boolean modifyArtist(Long idolId, String name, Boolean unit, Boolean gender) {
		return modifyIdol(idolId, name, unit, gender);
	}

	// follow/following
	public boolean followIdol(Long userId, Long idolId) {
		User user = userRepository.findOneByUserId(userId);
		Idol idol = idolRepository.findByIdolId(idolId);
		if (user == null || idol == null) return false;

		user.followWith(idol);
		userRepository.save(user);
		return true;
	}

	public boolean unfollowIdol(Long userId, Long idolId) {
		User user = userRepository.findOneByUserId(userId);
		Idol idol = idolRepository.findByIdolId(idolId);
		if (user == null || idol == null) return false;

		userRepository.unfollowIdol(userId, idolId);
		return true;
	}

	public List<Long> getUserFollowIdolList(Long userId) {
		List<Long> userIds = new ArrayList<>();

		User user = userRepository.findOneByUserId(userId);
		if (user == null) return userIds;

		return userRepository.getFollowIdolList(userId);
	}

	public Boolean isUserFollowIdol(Long userId, Long idolId) {
		User user = userRepository.findOneByUserId(userId);
		Idol idol = idolRepository.findByIdolId(idolId);
		if (user == null || idol == null) return false;

		if (userRepository.isFollowIdol(userId, idolId) <= 0)
			return false;

		return true;
	}

	// follower/followed
	public int getIdolFollowerCount(Long idolId) {
		Idol idol = idolRepository.findByIdolId(idolId);
		if (idol == null) return 0;

		return idolRepository.getFollowerCount(idolId);
	}

	public List<Long> getIdolFollowerUserList(Long idolId) {
		List<Long> userIds = new ArrayList<>();

		Idol idol = idolRepository.findByIdolId(idolId);
		if (idol == null) return userIds;

		return idolRepository.getFollowerList(idolId);
	}

	//// item; goods
	public boolean addItem(Long itemId, String name, Integer category, Long idolId, Long userId) {
		Item check = itemRepository.findByItemId(itemId);
		if (check != null) return false; // exist

		Item item = new Item(itemId, name, category, idolId, userId);
		Item ret = itemRepository.save(item);
		if (ret == null) return false;

		return true;
	}

	public boolean deleteItem(Long itemId) {
		Item item = itemRepository.findByItemId(itemId);
		if (item == null) return false; // not exist

		itemRepository.deleteByItemId(itemId);
		return true;
	}

	public boolean modifyItem(Long itemId, String name, Integer category, Long idolId, Long userId) {
		Item item = itemRepository.findByItemId(itemId);
		if (item == null) return false; // not exist

		Item ret = itemRepository.updateByItemId(itemId, name, category, idolId, userId);
		if (ret == null) return false;

		return true;
	}

	public boolean addGoods(Long itemId, String name, Integer category, Long idolId, Long userId) {
		return addItem(itemId, name, category, idolId, userId);
	}
	public boolean deleteGoods(Long itemId) {
		return deleteItem(itemId);
	}
	public boolean modifyGoods(Long itemId, String name, Integer category, Long idolId, Long userId) {
		return modifyItem(itemId, name, category, idolId, userId);
	}

	// follow/following
	public boolean followItem(Long userId, Long itemId) {
		User user = userRepository.findOneByUserId(userId);
		Item item = itemRepository.findByItemId(itemId);
		if (user == null || item == null) return false;

		userRepository.followItem(userId, itemId);
		return true;
	}

	public boolean unfollowItem(Long userId, Long itemId) {
		User user = userRepository.findOneByUserId(userId);
		Item item = itemRepository.findByItemId(itemId);
		if (user == null || item == null) return false;

		userRepository.unfollowItem(userId, itemId);
		return true;
	}

	public List<Long> getUserFollowItemList(Long userId) {
		List<Long> userIds = new ArrayList<>();

		User user = userRepository.findOneByUserId(userId);
		if (user == null) return userIds;

		return userRepository.getFollowItemList(userId);
	}

	public Boolean isUserFollowItem(Long userId, Long itemId) {
		User user = userRepository.findOneByUserId(userId);
		Item item = itemRepository.findByItemId(itemId);
		if (user == null || item == null) return false;

		if (userRepository.isFollowItem(userId, itemId) <= 0)
			return false;

		return true;
	}

	// follower/followed
	public int getItemFollowerCount(Long itemId) {
		Item item = itemRepository.findByItemId(itemId);
		if (item == null) return 0;

		return itemRepository.getFollowerCount(itemId);
	}

	//// meet; social-ing
	public boolean addMeet(Long meetId, String name, Long idolId, Long userId) {
		Meet check = meetRepository.findByMeetId(meetId);
		if (check != null) return false; // exist

		Meet meet = new Meet(meetId, name, idolId, userId);
		Meet ret = meetRepository.save(meet);
		if (ret == null) return false;

		return true;
	}

	public boolean deleteMeet(Long meetId) {
		Meet meet = meetRepository.findByMeetId(meetId);
		if (meet == null) return false; // not exist

		meetRepository.deleteByMeetId(meetId);
		return true;
	}

	public boolean modifyMeet(Long meetId, String name, Long idolId, Long userId) {
		Meet meet = meetRepository.findByMeetId(meetId);
		if (meet == null) return false; // not exist

		Meet ret = meetRepository.updateByMeetId(meetId, name, idolId, userId);
		if (ret == null) return false;

		return true;
	}

	public boolean addSocailing(Long meetId, String name, Long idolId, Long userId) {
		return addMeet(meetId, name, idolId, userId);
	}
	public boolean deleteSocailing(Long meetId) {
		return deleteMeet(meetId);
	}
	public boolean modifySocailing(Long meetId, String name, Long idolId, Long userId) {
		return modifyMeet(meetId, name, idolId, userId);
	}

	// following meet
	public boolean followMeet(Long userId, Long meetId) {
		User user = userRepository.findOneByUserId(userId);
		Meet meet = meetRepository.findByMeetId(meetId);
		if (user == null || meet == null) return false;

		userRepository.followMeet(userId, meetId);
		return true;
	}

	public boolean unfollowMeet(Long userId, Long meetId) {
		User user = userRepository.findOneByUserId(userId);
		Meet meet = meetRepository.findByMeetId(meetId);
		if (user == null || meet == null) return false;

		userRepository.unfollowMeet(userId, meetId);
		return true;
	}

	public List<Long> getUserFollowMeetList(Long userId) {
		List<Long> userIds = new ArrayList<>();

		User user = userRepository.findOneByUserId(userId);
		if (user == null) return userIds;

		return userRepository.getFollowMeetList(userId);
	}

	public Boolean isUserFollowMeet(Long userId, Long meetId) {
		User user = userRepository.findOneByUserId(userId);
		Meet meet = meetRepository.findByMeetId(meetId);
		if (user == null || meet == null) return false;

		if (userRepository.isFollowMeet(userId, meetId) <= 0)
			return false;

		return true;
	}

	// follower/followed
	public int getMeetFollowerCount(Long meetId) {
		Meet meet = meetRepository.findByMeetId(meetId);
		if (meet == null) return 0;

		return meetRepository.getFollowerCount(meetId);
	}

	//// place
	public boolean addPlace(Long placeId, String name, String address, Long idolId) {
		Place check = placeRepository.findOneByPlaceId(placeId);
		if (check != null) return false; // exist

		Place place = new Place(placeId, name, address, idolId);
		Place ret = placeRepository.save(place);
		if (ret == null) return false;

		return true;
	}

	public boolean deletePlace(Long placeId) {
		Place place = placeRepository.findByPlaceId(placeId);
		if (place == null) return false; // not exist

		placeRepository.deleteByPlaceId(placeId);
		return true;
	}

	public boolean modifyPlace(Long placeId, String name, String address, Long idolId) {
		Place place = placeRepository.findByPlaceId(placeId);
		if (place == null) return false; // not exist

		Place ret = placeRepository.updateByPlaceId(placeId, name, address, idolId);
		if (ret == null) return false;

		return true;
	}

	// follow/following
	public boolean followPlace(Long userId, Long placeId) {
		User user = userRepository.findOneByUserId(userId);
		Place place = placeRepository.findOneByPlaceId(placeId);
		if (user == null || place == null) return false;

		userRepository.followPlace(userId, placeId);
		return true;
	}

	public boolean unfollowPlace(Long userId, Long placeId) {
		User user = userRepository.findOneByUserId(userId);
		Place place = placeRepository.findOneByPlaceId(placeId);
		if (user == null || place == null) return false;

		userRepository.unfollowPlace(userId, placeId);
		return true;
	}

	public int getPlaceFollowerCount(Long placeId) {
		Place place = placeRepository.findByPlaceId(placeId);
		if (place == null) return 0;

		return placeRepository.getFollowerCount(placeId);
	}

	public List<Long> getUserFollowPlaceList(Long userId) {
		List<Long> userIds = new ArrayList<>();

		User user = userRepository.findOneByUserId(userId);
		if (user == null) return userIds;

		return userRepository.getFollowPlaceList(userId);
	}

	public Boolean isUserFollowPlace(Long userId, Long placeId) {
		User user = userRepository.findOneByUserId(userId);
		Place place = placeRepository.findByPlaceId(placeId);
		if (user == null || place == null) return false;

		if (userRepository.isFollowPlace(userId, placeId) <= 0)
			return false;

		return true;
	}

	public void deleteAll() {
		userRepository.deleteAll();
		idolRepository.deleteAll();
		itemRepository.deleteAll();
		meetRepository.deleteAll();
		placeRepository.deleteAll();
	}
}
