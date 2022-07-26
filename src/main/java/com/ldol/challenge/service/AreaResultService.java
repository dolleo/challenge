package com.ldol.challenge.service;

import com.ldol.challenge.domain.AreaResult;

public interface AreaResultService {

    void save(AreaResult areaResult);

    AreaResult findById(String id);
}
