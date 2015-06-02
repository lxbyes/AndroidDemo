package me.leckie.demo;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    private int x;

    private int y;

    @Override
    protected void setUp() throws Exception {
        x = 2;
        y = 3;
    }

    public void testAdd() {
        Assert.assertEquals(x + y, 5);
    }
}