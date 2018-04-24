package com.example.kzvda.menumanagementsystem;

import java.util.LinkedList;

public class Data {
    public static LinkedList<Object[]> getSettingsList() {
        LinkedList<Object[]> settingsList = new LinkedList<>();
        Object[] setting = new Object[3];
        setting[0] = R.string.change_personal_info;
        setting[1] = new boolean[]{true, true, true};
        setting[2] = 1;
        settingsList.add(setting);
        setting = new Object[3];
        setting[0] = R.string.manage_notifications;
        setting[1] = new boolean[]{true, false, false};
        setting[2] = 2;
        settingsList.add(setting);
        setting = new Object[3];
        setting[0] = R.string.support;
        setting[1] = new boolean[]{true, true, false};
        setting[2] = 3;
        settingsList.add(setting);
        setting = new Object[3];
        setting[0] = R.string.delete_account;
        setting[1] = new boolean[]{true, true, true};
        setting[2] = 4;
        settingsList.add(setting);
        setting = new Object[3];
        setting[0] = R.string.log_out;
        setting[1] = new boolean[]{true, true, true};
        setting[2] = 5;
        settingsList.add(setting);

        return settingsList;
    }

    public static LinkedList<Object[]> getAdminPageList() {
        LinkedList<Object[]> settingsList = new LinkedList<>();
        Object[] setting = new Object[2];
        setting[0] = R.string.change_menu;
        setting[1] = 6;
        settingsList.add(setting);
        setting = new Object[2];
        setting[0] = R.string.send_notification;
        setting[1] = 7;
        settingsList.add(setting);
        setting = new Object[2];
        setting[0] = R.string.edit_restaurant_info;
        setting[1] = 8;
        settingsList.add(setting);
        setting = new Object[2];
        setting[0] = R.string.manage_templates;
        setting[1] = 9;
        settingsList.add(setting);

        return settingsList;
    }
}
