package com.example.stepbystep;

import java.io.IOException;

public interface Closure<S, T, U>
{
    public S invoke(T caller, U data) throws IOException;
    
    Class<U> getResponseType();
}