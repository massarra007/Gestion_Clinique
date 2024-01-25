package spring.project.entities;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="users")
public class User {
	//@Id   
	private String username;
    private String password;
    private int enabled;
    //@ManyToMany(fetch = FetchType.EAGER)
    private Collection<Authority> roles = new ArrayList<>();
	

}
