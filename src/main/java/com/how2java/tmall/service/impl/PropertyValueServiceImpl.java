package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.PropertyValueMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product product){
     List<Property>properties =propertyService.list(product.getCid());

     for(Property property:properties){
         PropertyValue propertyValue =get(property.getId(),product.getId());
         if(propertyValue==null){
             propertyValue = new PropertyValue();
             propertyValue.setPid(product.getId());
             propertyValue.setPtid(property.getId());
             propertyValueMapper.insert(propertyValue);
          }
      }
    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValue get(int propertyId, int productId) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(propertyId).andPidEqualTo(productId);

        List<PropertyValue> pvs= propertyValueMapper.selectByExample(example);
        if (pvs.isEmpty())
            return null;
        return pvs.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> result = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : result) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return result;
    }



}
