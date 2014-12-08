/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nube.sample.service.employee;

import com.nube.core.annotations.MicroService;

/**
 *
 * @author incognito
 */
@MicroService(path = "/employee", port = 11000)
public class EmployeeService {

    public String getNubeEmployeeInfo(String ssn) {
        System.out.println("Employee comingin " + ssn);

        return "Hello";
    }

}
