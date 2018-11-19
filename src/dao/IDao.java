/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author salah
 */
public interface IDao <S>{
    boolean create(S o);
    boolean update(S o);
    boolean delete(S o);
    List<S> findAll();
    S findById(int id);
}
