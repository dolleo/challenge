package com.ldol.challenge.service.impl;

import com.ldol.challenge.domain.AreaResult;
import com.ldol.challenge.repository.AreaResultRepository;
import com.ldol.challenge.service.AreaResultService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AreaResultServiceImpl implements AreaResultService {

    private final AreaResultRepository areaResultRepository;

    public AreaResultServiceImpl(AreaResultRepository areaResultRepository) {
        this.areaResultRepository = areaResultRepository;
    }

    @Override
    public void save(AreaResult areaResult) {
        areaResultRepository.save(areaResult);
    }

    @Override
    public AreaResult findById(String id) {
        return areaResultRepository.findById(id).orElse(null);
    }
}
