package spring.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="authorities")
public class Authority {
//	@GeneratedValue
//	@Id
	private long idAuthority;
	
	private String Authority;
	
	//@ManyToOne
	User user;

	public Authority(String authority) {
		super();
		Authority = authority;
	}

	public Authority(String authority, User user) {
		super();
		Authority = authority;
		this.user = user;
	}
}
