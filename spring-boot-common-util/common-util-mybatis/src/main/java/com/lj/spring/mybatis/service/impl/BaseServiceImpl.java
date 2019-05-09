package com.lj.spring.mybatis.service.impl;


import com.lj.spring.mybatis.mapper.BaseComponentMapper;
import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import com.lj.spring.mybatis.service.BaseService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Collection;
import java.util.List;

/**
 * Created by lijun on 2019/5/8
 */
@NoArgsConstructor
public class BaseServiceImpl<T extends BaseEntityOnlyId> implements BaseService<T> {

    @Autowired
    private Mapper<T> baseMapper;

    @Autowired
    private BaseComponentMapper<T> baseComponentMapper;

    private Class<T> clazz;

    public BaseServiceImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Integer save(T t) {
        if (null == t) {
            return 0;
        }
        if (null == t.getId()) {
            return baseMapper.insertSelective(t);
        } else if (t.getId() > 0) {
            WeekendSqls<T> sql = WeekendSqls.custom();
            sql.andEqualTo(T::getId, t.getId());
            Example example = Example.builder(this.clazz)
                    .andWhere(sql)
                    .build();
            return updateByExampleSelective(t, example);
        }
        return 0;
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return baseMapper.selectByPrimaryKey(o);
    }

    @Override
    public List<T> selectByExample(Example example) {
        return baseMapper.selectByExample(example);
    }

    @Override
    public T selectOne(T t) {
        return baseMapper.selectOne(t);
    }

    @Override
    public int selectCount(T t) {
        return baseMapper.selectCount(t);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public int selectCountByExample(Example example) {
        return baseMapper.selectCountByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(T t) {
        return baseMapper.deleteByPrimaryKey(t);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByExample(Example example) {
        return baseMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKeys(Collection primaryKeys) {
        return baseMapper.deleteByPrimaryKey(primaryKeys);
    }

    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    @Override
    public int insertAll(List<T> list) {
        return baseComponentMapper.insertAll(list);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return baseMapper.updateByPrimaryKey(t);
    }

    @Override
    public int updateByExample(T t, Example example) {
        return baseMapper.updateByExample(t, example);
    }

    @Override
    public int updateByExampleSelective(T t, Example example) {
        return baseMapper.updateByExampleSelective(t, example);
    }
}
