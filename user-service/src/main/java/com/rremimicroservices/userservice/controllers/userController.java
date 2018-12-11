package com.rremimicroservices.userservice.controllers;

import com.rremimicroservices.userservice.entities.User;
import com.rremimicroservices.userservice.entities.userQuote;
import com.rremimicroservices.userservice.entities.userRepository;
import com.rremimicroservices.userservice.exceptions.SymbolDoesNotExistException;
import com.rremimicroservices.userservice.exceptions.SymbolIsNotValidException;
import com.rremimicroservices.userservice.exceptions.UserAlreadyExists;
import com.rremimicroservices.userservice.exceptions.UserNotFoundException;
import com.rremimicroservices.userservice.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class userController {
    private userRepository userRepository; //user repository service
    @PersistenceContext
    private EntityManager entityManager; //for updating user details
    @Autowired
    private UrlUtil util; //a small utility to get the address from eureka when given the service id

    @Autowired
    public userController(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users") //lists all users in the database
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/addUser") //allows to add a user
    public User addUser(@RequestBody User user){
        if (userRepository.existsByEmail(user.getEmail())) throw new UserAlreadyExists(user.getEmail());
        //is symbols field is present check them to see if they are valid
        if (user.getSymbols().size() >0) {
            for (String symbol : user.getSymbols()) {
                if (!isSymbolValid(symbol)) throw new SymbolIsNotValidException(symbol);
            }
        }
        //change all given symbols to uppercase
        Set<String> sy = new HashSet<>(user.getSymbols().size());
        for (String s :user.getSymbols()) sy.add(s.toUpperCase());
        user.addAllSymbols(sy);
        userRepository.save(user);
        return userRepository.findById(user.getId()).get();
    }

    @Transactional
    @PostMapping("/addQuote/{userId}") //allows to add new quotes for a user,user is chosen fro his id
    public User addQuotesForUser(@RequestBody String symbol, @PathVariable(name = "userId")Long id){
    User u = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    if (!isSymbolValid(symbol)) throw new SymbolIsNotValidException(symbol);//check the validity of quote's symbols
        //if users tries to add a quote which he already has it does not add it
        if (!u.getSymbols().contains(symbol)) {
            u.addSymbol(symbol.toUpperCase());
            entityManager.merge(u);
            entityManager.close();
        }
    return userRepository.findById(id).get();
    }

    @GetMapping("/userQuotes/{id}") //returns the details of quotes for a specific user
    public Set<userQuote> getUserQuotes(@PathVariable(name = "id") Long id){
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        Set<userQuote> userQuotes = new HashSet<>();
        //contact the quote-service to ge tthe details for the user's quotes
        String url = util.getServiceUrl("quote-service");
        RestTemplate template =  new RestTemplate();
        for (String symbol :user.getSymbols()){
            userQuotes.add(template.getForObject(url+"/quote/{symbol}",userQuote.class,symbol));
        }
        return userQuotes;
    }

    @Transactional
    @DeleteMapping("/deleteQuote/{id}") //delete a user when given his id
    public User removeUserQuote(@RequestBody String symbol,@PathVariable(name = "id") Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        if (!user.getSymbols().remove(symbol.toUpperCase())) throw new SymbolIsNotValidException(symbol);
        entityManager.merge(user);
        return user;
    }

    //contacts the quote-service to check the validity of a symbol(if it is a valid quote's symbol)
    private Boolean isSymbolValid(String symbol){
        RestTemplate restTemplate = new RestTemplate();
        String url = util.getServiceUrl("quote-service");
        return restTemplate.getForObject(url+"/validation/{symbol}",Boolean.class,symbol);
    }

    //sends the error message when user already exists
    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Message userExists(UserAlreadyExists u){
        return new Message(409,"User with email "+u.getEmail()+" already exists");
    }

    //sends an error message when user does not exist
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Message userNotFound(UserNotFoundException u){
        return new Message(404,"User with id "+u.getId()+" does not exist");
    }

    //sends an error message when given symbol is not valid
    @ExceptionHandler(SymbolIsNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message invalidSymbol(SymbolIsNotValidException u){
        return new Message(400,"Given symbol :"+u.getSymbol().toUpperCase()+" is not a valid quote's symbol");
    }

    //sends an error message when symbol that user wants to delete does not exists
    @ExceptionHandler(SymbolDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message symbolNotExist(SymbolDoesNotExistException u){
        return new Message(400,"User does not have a quote with symbol "+u.getSymbol().toUpperCase());
    }

}
