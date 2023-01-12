package com.solvd.onlinestore.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResourceDoesNotExistException extends RuntimeException {

    private final String message;

}
