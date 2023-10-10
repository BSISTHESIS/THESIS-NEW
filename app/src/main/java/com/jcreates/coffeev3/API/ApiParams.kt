package com.jcreates.houseofcoffee.API

class ApiParams {
    var params: MutableMap<String, String> = HashMap()


    fun getUserData(username: String, password: String): Map<String, String> {
        params["action"] = "VALIDATE_LOGIN"
        params["appKey"] = appkey
        params["username"] = username
        params["password"] = password
        return params
    }

    fun getVersionCode(): Map<String, String> {
        params["action"] = "VERSION_CODE"
        params["appKey"] = appkey
        return params
    }

    fun getRequestData(action: String): Map<String, String> {
        params["action"] = action
        params["appKey"] = appkey
        return params
    }

    fun toServerPopular(data: String): Map<String,String> {
        params["action"] = "INSERT_POPULAR"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }

    fun updateNotificationStatus(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_NOTIF"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }
    fun updateStatusPopular(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_POPULAR"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }

    fun updateStatusPopular2(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_POPULAR_ITEMS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }

    fun toServerADS(data: String): Map<String, String> {
        params["action"] = "INSERT_ADS"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }



    // COFFEE START

    fun updateStatusCoffee(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_COFFEE"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }

    fun toServerCoffee(data: String): Map<String,String> {
        params["action"] = "INSERT_COFFFEE"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }

    fun updateStatusCoffee2(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_COFFEE_ITEMS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }



    //COFFEE END



    // NON COFFEE START

    fun updateStatusNONCoffee(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_NON_COFFEE"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }

    fun toServerNONCoffee(data: String): Map<String,String> {
        params["action"] = "INSERT_NON_COFFFEE"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }

    fun updateStatusNONCoffee2(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_NON_COFFEE_ITEMS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }



    //NON COFFEE END


    // WAFFLE START

    fun updateStatusWaffle(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_WAFFLE"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }

    fun toServerWaffle(data: String): Map<String,String> {
        params["action"] = "INSERT_WAFFLE"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }

    fun updateStatusWaffle2(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_WAFFLE_ITEMS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }



    //WAFFLE END

// FRIES START

    fun updateStatusFries(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_FRIES"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }


    fun updateStatusFries2(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_FRIES_ITEMS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }
    fun toServerFries(data: String): Map<String,String> {
        params["action"] = "INSERT_FRIES"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }



    //DRINKS END


    fun updateStatusDrinks(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_DRINKS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }

    fun toServerDrinks(data: String): Map<String,String> {
        params["action"] = "INSERT_DRINKS"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }

    fun updateStatusDrinks2(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_DRINKS_ITEMS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }



    //DRINKS END
    fun toServerSLideImage(data: String): Map<String,String> {
        params["action"] = "INSERT_SLIDE_IMAGE"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }


    fun updateStatusImageSlide(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_SLIDE"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }


    fun updateStatusImageSlide2(account_id: String): Map<String, String> {
        params["action"] = "UPDATE_SLIDE_ITEMS"
        params["appKey"] = appkey
        params["ids"] = account_id
        return params
    }


    fun toServerOrders(data: String): Map<String,String> {
        params["action"] = "INSERT_ORDER"
        params["appKey"] = appkey
        params["json_data"] = data
        return params
    }


    fun updateStatusOrder(account_id: String,total_payment: String, payment : String , change:String): Map<String, String> {
        params["action"] = "UPDATE_STATUS_ORDER"
        params["appKey"] = appkey
        params["ids"] = account_id
        params["total_payment"] = total_payment
        params["payment"] = payment
        params["change"] = change
        return params
    }


    fun getListAccountSales(account_id: String,account_id1: String): Map<String, String> {
        params["action"] = "SALES_ACCOUNT"
        params["appKey"] = appkey
        params["ids"] = account_id
        params["ids1"] = account_id1
        return params
    }
    companion object {
        private const val appkey = "@dm1nC0fF33"
    }
}

