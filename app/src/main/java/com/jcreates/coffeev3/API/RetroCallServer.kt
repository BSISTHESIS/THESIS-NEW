package com.joey.kotlinandroidbeginning.API


import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.jcreates.coffeev3.ADMIN_PANEL.AdminCashier
import com.jcreates.coffeev3.ADMIN_PANEL.AdminCoffee
import com.jcreates.coffeev3.ADMIN_PANEL.AdminDrinks
import com.jcreates.coffeev3.ADMIN_PANEL.AdminFries
import com.jcreates.coffeev3.ADMIN_PANEL.AdminImage
import com.jcreates.coffeev3.ADMIN_PANEL.AdminNonCoffee
import com.jcreates.coffeev3.ADMIN_PANEL.AdminPopular
import com.jcreates.coffeev3.ADMIN_PANEL.AdminWaffle
import com.jcreates.coffeev3.Splash.ImageSlide
import com.jcreates.houseofcoffee.API.ApiParams
import com.joey.kotlinandroidbeginning.API.WebService.apiLink
import com.joey.noteapplication.data.viewModel.AdsOnViewModel
import com.joey.noteapplication.data.viewModel.CashierListOrderViewModel
import com.joey.noteapplication.data.viewModel.CashierViewModel
import com.joey.noteapplication.data.viewModel.CoffeeViewModel
import com.joey.noteapplication.data.viewModel.DrinksViewModel
import com.joey.noteapplication.data.viewModel.FriesViewModel
import com.joey.noteapplication.data.viewModel.ImageViewModel
import com.joey.noteapplication.data.viewModel.NonCoffeeViewModel
import com.joey.noteapplication.data.viewModel.PopularViewModel
import com.joey.noteapplication.data.viewModel.WaffleViewModel


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONException

class RetroCallServer(private val _activity: Activity) {
    var listing: String? = null
    var final_: String? = null
    var final_ids: String=""
    var _defaultResponse: Observable<ResponseModels.DefaultResponse>? = null
    var userResponseObservable: Observable<ResponseModels.UserResponse>? = null
    var appVersionCodeObservable: Observable<ResponseModels.AppVersionCode>? = null
    private val _apiInterface: ApiInterface
    var apiParams: ApiParams? = null
//    private var currentUser: CurrentUserRepository? = null
    private var popularViewModel: PopularViewModel?=null
    private  var coffeeViewModel: CoffeeViewModel? =null
    private var nonCoffeeViewModel: NonCoffeeViewModel?=null
    private var waffleViewModel: WaffleViewModel?=null
    private var friesViewModel: FriesViewModel?=null
    private var drinksViewModel: DrinksViewModel?=null
    private var addOnViewModel: AdsOnViewModel? = null
    private var imageViewModel : ImageViewModel?=null
    private var cashierViewModel:CashierViewModel?=null
    private var cashierListOrderViewModel:CashierListOrderViewModel?=null
//    val loading = LoadingDialog(_activity)
    init {
//        clientRepository = ClientSearchRepository(_activity.application)
        _apiInterface = apiLink!!.create(ApiInterface::class.java)
    popularViewModel = PopularViewModel(_activity.application)
    coffeeViewModel = CoffeeViewModel(_activity.application)
    nonCoffeeViewModel = NonCoffeeViewModel(_activity.application)
    waffleViewModel = WaffleViewModel(_activity.application)
    friesViewModel = FriesViewModel(_activity.application)
    drinksViewModel = DrinksViewModel(_activity.application)
    addOnViewModel = AdsOnViewModel(_activity.application)
    imageViewModel = ImageViewModel(_activity.application)
    cashierViewModel = CashierViewModel(_activity.application)
    cashierListOrderViewModel = CashierListOrderViewModel(_activity.application)
    }



//    fun RequesttoLogin(username: String?, password: String?,progressDialog: ProgressDialog ) {
//        userResponseObservable = _apiInterface.requestToServer(ApiParams().getUserData(username!!, password!!))
//        userResponseObservable!!.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<ResponseModels.UserResponse> {
//                override fun onSubscribe(d: Disposable) {}
//                override fun onNext(userResponse: ResponseModels.UserResponse) {
//
//                        if (userResponse.message.equals("SUCCESS")) {
//                            val currentUsers = CurrentUser(0,userResponse.employeeID.toString(),userResponse.full_name.toString(),userResponse.pos_value.toString(),0,userResponse.dep_value.toString(),userResponse.unit_value.toString(),userResponse.zimbra.toString())
//                            viewModel.insert(currentUsers)
//
//                            forMapp(userResponse.employeeID.toString());
//                        } else {
//                            Toast.makeText(
//                                _activity, userResponse.message.toString(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        progressDialog.dismiss()
//                        Log.d("LOGIN", userResponse.message.toString())
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.d("LOGIN", e.toString())
//                }
//
//                override fun onComplete() {
//                }
//            })
//    }





    fun RequestData(action: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().getRequestData(action))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    Log.d("requestLoc", response.getData().toString())
                    if (response.statusCode == "2000") {
                        when (action) {
                            "GET_POPULAR_LIST" -> try {
                                popularViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_COFFEE_LIST" -> try {
                                coffeeViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_NON_COFFEE_LIST" -> try {
                                nonCoffeeViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_WAFFLE_LIST" -> try {
                                waffleViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_FRIES_LIST" -> try {
                                friesViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_DRINKS_LIST" -> try {
                                drinksViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_ADD_ONS" -> try {
                                addOnViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_SLIDE_IMAGE_LIST" -> try {
                                imageViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_LIST_ORDER" -> try {
                                cashierViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            "GET_LIST_CASHIER_ORDER" -> try {
                                cashierListOrderViewModel?.insert(JSONArray(response.getData().toString()))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }


                        }
                    } else {

                        Log.d("VALUE_lOCATION", response.message!!)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUE", "Server Calls ErrorzzzREQUEST: " + e.localizedMessage)
                }

                override fun onComplete() {
                    Log.d("COMPLETE", "Complete Location Address")
                }
            })
    }

    fun SendDataPopular(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerPopular(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_POPULAR_LIST")
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminPopular::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }



    fun RequestUpdateStatusPopular(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusPopular(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_POPULAR_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminPopular::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusPopular2(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusPopular2(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_POPULAR_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY DELETE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminPopular::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }

    //COFFEE START
    fun SendDataCoffee(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerCoffee(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_COFFEE_LIST")
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminCoffee::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }



    fun RequestUpdateStatusCoffee(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusCoffee(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_COFFEE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminCoffee::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusCoffee2(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusCoffee2(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_COFFEE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY DELETE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminCoffee::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    //COFFEE END





    //NON COFFEE START
    fun SendDataNonCoffee(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerNONCoffee(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_NON_COFFEE_LIST")
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminNonCoffee::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }



    fun RequestUpdateStatusNonCoffee(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusNONCoffee(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_NON_COFFEE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminNonCoffee::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusNonCoffee2(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusNONCoffee2(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_NON_COFFEE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY DELETE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminNonCoffee::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    //NON COFFEE END




    //WAFFLE START
    fun SendDataWaffle(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerWaffle(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_WAFFLE_LIST")
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminWaffle::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }



    fun RequestUpdateStatusWaffle(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusWaffle(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_WAFFLE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminWaffle::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusWaffle2(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusWaffle2(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_WAFFLE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY DELETE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminWaffle::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    //WAFFLE END



    //FRIES START
    fun SendDataFries(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerFries(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_FRIES_LIST")
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminFries::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }



    fun RequestUpdateStatusFries(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusFries(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_FRIES_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminFries::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusFries2(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusFries2(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_FRIES_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY DELETE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminFries::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    //FRIES END



    //DRINKS START
    fun SendDataDrinks(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerDrinks(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_DRINKS_LIST")
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminDrinks::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }



    fun RequestUpdateStatusDrinks(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusDrinks(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_DRINKS_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminDrinks::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusDrinks2(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusDrinks2(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_DRINKS_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY DELETE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminDrinks::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    //DRINKS END

    fun SendImageSLide(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerSLideImage(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_SLIDE_IMAGE_LIST")
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminImage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusImageSlide(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusImageSlide(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_SLIDE_IMAGE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminImage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }


    fun RequestUpdateStatusImageSlide2(account_id: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusImageSlide2(account_id))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        RequestData("GET_SLIDE_IMAGE_LIST")
                        Toast.makeText(_activity,"SUCCESSFULLY DELETE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity, AdminImage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }

    fun SendDataOrder(jsonData: String) {
        _defaultResponse = _apiInterface.getData(ApiParams().toServerOrders(jsonData))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("asas", response.message.toString())
                        Toast.makeText(
                            _activity.applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        _activity.applicationContext,
                        "Error: " + e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    //loadingDialog.DismissDialog();
                    Log.d("eeror", e.localizedMessage)
                }

                override fun onComplete() {
//                    val intent = Intent(_activity,ImageSlide::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    _activity.application.startActivity(intent)
                }
            })
    }


    fun updateStatusOrder(account_id: String,total_payment: String, payment : String , change:String) {
        _defaultResponse = _apiInterface.getData(ApiParams().updateStatusOrder(account_id,total_payment,payment,change))
        _defaultResponse!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModels.DefaultResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(response: ResponseModels.DefaultResponse) {
                    if (response.statusCode.equals("2000")) {
                           RequestData("GET_LIST_CASHIER_ORDER")
//                        Toast.makeText(_activity,"SUCCESSFULLY CHANGE STATUS",Toast.LENGTH_LONG).show()
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("VALUEss", "Server Calls Errorz: " + e.localizedMessage)
                }

                override fun onComplete() {
                    val intent = Intent(_activity,AdminCashier::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    _activity.application.startActivity(intent)
                }
            })
    }

    companion object {
        private val appkey: String = WebService.appKey
    }
}