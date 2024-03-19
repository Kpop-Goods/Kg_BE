package hello.kpop.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IdolRepository idolRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private MeetRepository meetRepository;
	@Autowired
	private PlaceRepository placeRepository;

	// add user
	public boolean addUser(Long userId, String name, String email) {
		User check = userRepository.findOneByUserId(userId);
		if (check != null) return false; // exist

		User user = new User(userId, name, email);
		User ret = userRepository.save(user);
		if (ret == null) return false;

		return true;
	}

	// following user
	public boolean followUser(Long followerId, Long targetId) {
		User follower = userRepository.findOneByUserId(followerId);
		User target   = userRepository.findOneByUserId(targetId);
		if (follower == null) return false;
		if (target   == null) return false;

		follower.followWith(target);
		userRepository.save(follower);
		return true;
	}

	// add idol
	public boolean addIdol(Long idolId, String name, Boolean unit, Boolean gender) {
		Idol check = idolRepository.findOneByIdolId(idolId);
		if (check != null) return false; // exist

		Idol idol = new Idol(idolId, name, unit, gender);
		Idol ret = idolRepository.save(idol);
		if (ret == null) return false;

		return true;
	}

	public boolean addArtist(Long idolId, String name, Boolean unit, Boolean gender) {
		return addIdol(idolId, name, unit, gender);
	}

	// following idol
	public boolean followIdol(Long userId, Long idolId) {
		User user = userRepository.findOneByUserId(userId);
		Idol idol = idolRepository.findByIdolId(idolId);
		if (user == null || idol == null) return false;

		user.followWith(idol);
		userRepository.save(user);
		return true;
	}

	// add place
	public boolean addPlace(Long placeId, String name, String address, Long idolId) {
		Place check = placeRepository.findOneByPlaceId(placeId);
		if (check != null) return false; // exist

		Place place = new Place(placeId, name, address, idolId);
		Place ret = placeRepository.save(place);
		if (ret == null) return false;

		return true;
	}

	// add item; goods
	public boolean addItem(Long itemId, String name, Integer category, Long idolId, Long userId) {
		Item check = itemRepository.findByItemId(itemId);
		if (check != null) return false; // exist

		Item item = new Item(itemId, name, category, idolId, userId);
		Item ret = itemRepository.save(item);
		if (ret == null) return false;

		return true;
	}

	public boolean addGoods(Long itemId, String name, Integer category, Long idolId, Long userId) {
		return addItem(itemId, name, category, idolId, userId);
	}

	// add meet; social-ing
	public boolean addMeet(Long meetId, String name, Long idolId, Long userId) {
		Meet check = meetRepository.findByMeetId(meetId);
		if (check != null) return false; // exist

		Meet meet = new Meet(meetId, name, idolId, userId);
		Meet ret = meetRepository.save(meet);
		if (ret == null) return false;

		return true;
	}

	public boolean addSocailing(Long meetId, String name, Long idolId, Long userId) {
		return addMeet(meetId, name, idolId, userId);
	}
}
