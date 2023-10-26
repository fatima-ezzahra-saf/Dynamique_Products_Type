package com.example.prj2prod_type.controllers;

import com.example.prj2prod_type.entities.Type;
import com.example.prj2prod_type.repository.RepositoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("localhost:3000")
public class productController {
    @Autowired
    RepositoryType repositoryType;
    @Autowired
    JdbcTemplate jdbcTemplate;
    //creation d'un nouveau type
    @PostMapping("/create_type/{typeName}")
    public void createType(@RequestBody List<String> new_type, @PathVariable String typeName){
        Type type=new Type();
        type.setNom(typeName);
        repositoryType.save(type);
        String sql1="";
        for (int i=0;i<new_type.size();i++){
            if(i == new_type.size()-1) {
                sql1 += new_type.get(i).toString() + " varchar(225) );";
            }else{
            sql1+=new_type.get(i).toString()+" varchar(225) , ";
            }
        }
        String sql="Create Table"+" "+typeName+"( "+sql1;
        jdbcTemplate.execute(sql);
    }
    //Avoir tous les types
    @GetMapping("/get_type")
    public List<Type> getType(){
        return repositoryType.findAll();
    }
    //selectionner les attributs d_un table
    @PostMapping("/get_attributs/{nom}")
    public List<String> productCarac(@PathVariable String nom){
        List<String> columnNames = new ArrayList<>();
        jdbcTemplate.query(
                "SELECT column_name FROM information_schema.columns WHERE table_name = ?",
                new Object[]{nom},
                (rs, rowNum) -> {
                    columnNames.add(rs.getString("column_name"));
                    return null;
                }
        );

        return columnNames;
    }
    //inseret un produit on donnant son type
    @PostMapping("/insertProduct/{nom}")
    public void insertproduct(@RequestBody List<String> list,@PathVariable String nom){
        List<String> l=productCarac(nom);
        String sql3="";
        String sql4="";
        for(int i=0;i<list.size();i++){
            if (i==list.size()-1){
                sql3+=l.get(i).toString();
                sql4+="'"+list.get(i).toString()+"'";
            }else {
                sql3 +=l.get(i).toString() + " ,";
                sql4 += "'"+list.get(i).toString() + " ',";
            }

        }
        String sql = "Insert into "+nom+" ("+sql3+") Values ("+sql4+" );";
        jdbcTemplate.execute(sql);
    }
    //Recuperer les produits de ce type
    @GetMapping("/getProductsByTypes/{nom}")
    public List<Map<String, Object>> getAllProduct(@PathVariable String nom) {
        String sql = "SELECT * FROM " + nom;
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        return results;
    }

}
