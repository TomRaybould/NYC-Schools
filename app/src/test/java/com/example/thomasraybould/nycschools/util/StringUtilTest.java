package com.example.thomasraybould.nycschools.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    private String invalid1 = "";
    private String invalid2 = null;
    private String invalid3 = "   ";

    private String valid1 = "valid";
    private String valid2 = " valid";

    @Test
    public void isStringValid() {
        Assert.assertTrue(!StringUtil.isStringValid(invalid1));
        Assert.assertTrue(!StringUtil.isStringValid(invalid2));
        Assert.assertTrue(!StringUtil.isStringValid(invalid3));
        Assert.assertTrue(StringUtil.isStringValid(valid1));
        Assert.assertTrue(StringUtil.isStringValid(valid2));
    }

    @Test
    public void areStringValid() {
        Assert.assertTrue(!StringUtil.areStringsValid(valid1, invalid1));
        Assert.assertTrue(StringUtil.areStringsValid(valid1, valid2));
        Assert.assertTrue(StringUtil.areStringsValid(valid2));
    }
}