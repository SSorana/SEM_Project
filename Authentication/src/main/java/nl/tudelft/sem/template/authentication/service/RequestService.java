package nl.tudelft.sem.template.authentication.service;

import java.util.Optional;
import nl.tudelft.sem.template.authentication.entities.UserContainer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface which fetches a user from the user microservice by name.
 */
@FeignClient(name = "authentication-microservice")
@Service
public interface RequestService {

    /**
     * Fetches user from user microservice.
     *
     * @param userName username.
     * @return UserDTO
     */
    @RequestMapping("/getByUsername/{userName}")
    Optional<UserContainer> getByUsername(@PathVariable("userName") String userName);

    //    //method still to be implemented
    //    void updateUser(UserContainer user);
}