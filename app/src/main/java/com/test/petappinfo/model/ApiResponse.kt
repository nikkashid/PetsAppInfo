package com.nikhil.petsinfoapp.model

import com.nikhil.petsinfoapp.util.State

/**
 * Response data class which provides response state and data
 */
class ApiResponse<T> (val data : T, val state : State)