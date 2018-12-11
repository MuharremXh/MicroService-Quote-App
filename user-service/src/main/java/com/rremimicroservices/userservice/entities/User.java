package com.rremimicroservices.userservice.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Access(AccessType.FIELD)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;

    @ElementCollection
    private Set<String> symbols = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getSymbols() {
        return symbols;
    }

    public void addSymbol(String symbol) {
        symbols.add(symbol);
    }

    public void addAllSymbols(Set<String> symbols){
        this.symbols = symbols;
    }

    public boolean removeSymbol(String symbol){
        if (symbols.contains(symbol)){
            symbols.remove(symbol);
            return true;
        }
        return false;
    }
}
