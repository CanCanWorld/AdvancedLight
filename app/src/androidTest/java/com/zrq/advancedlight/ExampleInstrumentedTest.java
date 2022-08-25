package com.zrq.advancedlight;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.zrq.advancedlight.dao.IUserDao;
import com.zrq.advancedlight.dao.UserDaoImpl;
import com.zrq.advancedlight.entity.User;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";

    @Test
    public void testAddUser() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IUserDao userDao = new UserDaoImpl(appContext);
        User user = new User();
        user.setUsername("zrq");
        user.setPassword("zhang456...");
        user.setSex("male");
        user.setAge(21);
        long result = userDao.addUser(user);
        Log.d(TAG, "testAddUser: "+user);
        assertNotEquals(-1,result);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.zrq.advancedlight", appContext.getPackageName());
    }
}