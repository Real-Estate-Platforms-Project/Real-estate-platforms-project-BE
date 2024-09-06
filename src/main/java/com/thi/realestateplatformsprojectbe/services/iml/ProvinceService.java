package com.thi.realestateplatformsprojectbe.services.iml;

import com.thi.realestateplatformsprojectbe.models.Province;
import com.thi.realestateplatformsprojectbe.repositories.IProvinceRepository;
import com.thi.realestateplatformsprojectbe.services.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService implements IProvinceService {

    @Autowired
    private IProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        return provinceRepository.findAll();
    }
}
