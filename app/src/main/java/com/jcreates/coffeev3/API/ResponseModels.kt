package com.joey.kotlinandroidbeginning.API

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.google.gson.JsonArray
import java.io.Serializable

class ResponseModels {
    class DefaultResponse : Serializable {
        @SerializedName("message")
        var message: String? = null

        @SerializedName("statusCode")
        var statusCode: String? = null

        @SerializedName("data")
        @Expose
        private var data: JsonArray? = null
        fun getData(): Any? {
            return data
        }

        fun setData(data: JsonArray?) {
            this.data = data
        }
    }

    class UserResponse {
        @SerializedName("message")
        var message: String? = null

        @SerializedName("statusCode")
        var statusCode: String? = null

        @SerializedName("employeeID")
        var employeeID: String? = null

        @SerializedName("full_name")
        var full_name: String? = null

        @SerializedName("pos_value")
        var pos_value: String? = null

        @SerializedName("unit_value")
        var unit_value: String? = null

        @SerializedName("zimbra")
        var zimbra: String? = null

        @SerializedName("dep_value")
        var dep_value: String? = null

        fun getData(): Any? {
            return employeeID
        }
        fun setData(data: String?) {
            this.employeeID = data
        }

    }

    class AppVersionCode {
        @SerializedName("message")
        var message: String? = null

        @SerializedName("statusCode")
        var statusCode: String? = null

        @SerializedName("version_code")
        var version_code: String? = null
    }


}