package sonn.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
* @ClassName: Relation 
* @Description: the relation of users,
*          it's created when someone follows another one.
* @author sonne
* @date 2016-12-28 20:33:55 
* @version 1.0
 */
@Entity
public class Relation {
	
	/*id*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/* the person who is followed */
	@OneToOne(fetch = FetchType.EAGER)
	private User followed;
	
	/* the fan */
	@OneToOne(fetch = FetchType.EAGER)
	private User fan;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getFollowed() {
		return followed;
	}
	public void setFollowed(User followed) {
		this.followed = followed;
	}
	public User getFan() {
		return fan;
	}
	public void setFan(User fan) {
		this.fan = fan;
	}
	
}
