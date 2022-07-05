package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MenuInfo {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val TYPE_TRADE = 0x01

    /**
     * 联机管理类
     */
    val TYPE_MANAGER = 0x02

    /**
     * 本地类
     */
    val TYPE_LOCAL = 0x04


    /**
     * 菜单名称
     */
    private val menuName: String? = null

    /**
     * 菜单ID
     */
    private val menuId: String? = null

    /**
     * 父菜单ID
     */
    private val menuPid: String? = null

    /**
     * 交易类型ID
     */
    private val transName: String? = null

    /**
     * 菜单类型  0: 联机交易类 1: 联机管理类 2：本地菜单(非联机)
     */
    private val menuType = 0

    /**
     * 菜单图标对应的名称(无包括扩展名)
     */
    private val iconName: String? = null

    /**
     * 是否是菜单组
     */
    private val isMenuGroup = false

    /**
     * 是否在首页展示
     */
    private val isShowOnHome = false

    /**
     * 是否在子菜单中展示
     */
    private val isShowOnMenu = false

    /**
     * 显示顺序
     */
    private val sort = 0

}