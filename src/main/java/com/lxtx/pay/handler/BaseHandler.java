package com.lxtx.pay.handler;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 替代 com.qlzf.commons.handler.SimpleIbatisEntityHandler 的基类
 * 使用 MyBatis SqlSession 实现相同功能
 */
public abstract class BaseHandler<T> {

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    /**
     * 获取 Handler 类的全限定名作为 namespace
     * 与 Mapper XML 中的 namespace 保持一致
     */
    protected String getNamespace() {
        return this.getClass().getName();
    }

    /**
     * 查询单条记录（指定 statement）
     */
    protected <E> E queryForObject(String statement, Object parameter) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(statement, parameter);
        }
    }

    /**
     * 查询单条记录（无参数）
     */
    protected <E> E queryForObject(String statement) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(statement);
        }
    }

    /**
     * 查询列表（指定 statement）
     */
    protected <E> List<E> queryForList(String statement, Object parameter) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(statement, parameter);
        }
    }

    /**
     * 查询列表（无参数）
     */
    protected <E> List<E> queryForList(String statement) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList(statement);
        }
    }

    /**
     * 更新操作（指定 statement）
     */
    protected int update(String statement, Object parameter) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int result = session.update(statement, parameter);
            session.commit();
            return result;
        }
    }

    /**
     * 插入操作（指定 statement）
     */
    protected int insert(String statement, Object parameter) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int result = session.insert(statement, parameter);
            session.commit();
            return result;
        }
    }

    /**
     * 插入操作（使用默认 insert statement）
     * 兼容 SimpleIbatisEntityHandler 的单参数 insert(T) 方法
     */
    protected int insert(T entity) {
        return insert(getNamespace() + ".insert", entity);
    }

    /**
     * 删除操作（指定 statement）
     */
    protected int delete(String statement, Object parameter) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int result = session.delete(statement, parameter);
            session.commit();
            return result;
        }
    }

    /**
     * 删除操作（使用默认 deleteById statement）
     * 兼容 SimpleIbatisEntityHandler 的单参数 delete(ID) 方法
     */
    protected int delete(Object id) {
        return delete(getNamespace() + ".deleteById", id);
    }

    /**
     * 根据ID查询（使用默认 select statement）
     * 兼容 SimpleIbatisEntityHandler 的 select(ID) 方法
     */
    public T select(Object id) {
        String statement = getNamespace() + ".select";
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne(statement, id);
        }
    }
}
