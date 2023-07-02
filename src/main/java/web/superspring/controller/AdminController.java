package web.superspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import web.superspring.CreateStartUser.UserNotCreateEx;
import web.superspring.model.User;
import web.superspring.service.RoleService;
import web.superspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> allUsers = userService.listUsers();
        return ResponseEntity.ok(allUsers);
    }
    @GetMapping("/api/users/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable int id) {
        Optional<User> user = userService.show(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PostMapping("/api/users")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody  @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errorMessage.append(fieldErrors).append("::: \n"));
            throw new UserNotCreateEx(errorMessage.toString());
        }
        userService.add(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/api/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable int id) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/admin")
    public ModelAndView userPage2(ModelMap modelMap, Principal principal) {
        User user = userService.findUsersByEmail(principal.getName());
        modelMap.addAttribute("roles", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}