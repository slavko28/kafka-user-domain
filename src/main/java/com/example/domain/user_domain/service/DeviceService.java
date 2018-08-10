package com.example.domain.user_domain.service;

import com.example.domain.user_domain.model.Device;
import com.example.domain.user_domain.model.User;

public interface DeviceService {

    Device create(String descriptions, User user);

}
