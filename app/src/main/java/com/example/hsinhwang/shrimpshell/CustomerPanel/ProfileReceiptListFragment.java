package com.example.hsinhwang.shrimpshell.CustomerPanel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.OrderInstantDetail;
import com.example.hsinhwang.shrimpshell.Classes.OrderRoomDetail;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ProfileReceiptListFragment extends Fragment {
    private final static String TAG = "RoomListFragment";
    private String order_id;
    private int customerId;
    private FragmentActivity activity;
    private SharedPreferences preferences;
    private CommonTask roomDetailTask, instantDetailTask;
    private List<OrderRoomDetail> orderRoomDetails;
    private List<OrderInstantDetail> orderInstantDetails;
    private HashMap<String, HashMap<String, ArrayList<?>>> detailContainer = new HashMap<String, HashMap<String, ArrayList<?>>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        preferences = activity.getSharedPreferences(Common.LOGIN, MODE_PRIVATE);
        customerId = preferences.getInt("IdCustomer", 0);

        orderRoomDetails = showRoomDetailList();
        orderInstantDetails = showInstantDetailList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_receipt, container, false);

        showRefactoredData(); // 資料放在detailContainer裡面
        Log.d(TAG, String.valueOf(detailContainer));
//        handleViews(view);


//        /*
//        List<ProfileReceiptDetail> receiptList = new ArrayList<>();
//        receiptList.add(new ProfileReceiptDetail("Title 1", "Detail 1"));
//        receiptList.add(new ProfileReceiptDetail("Title 2", "Detail 2"));
//        receiptList.add(new ProfileReceiptDetail("Title 3", "Detail 3"));
//
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(new ReceiptAdapter(inflater, receiptList));*/
        return view;
    }
//
//    private void handleViews(View view) {
//        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        List<ProfileReceiptDetail> profileReceiptDetailList = getProfileReceiptDetailList();
//        recyclerView.setAdapter(new ProfileReceiptDetailAdapter(getContext(), profileReceiptDetailList));//設定Adapter
//    }
//
//
//    private class ProfileReceiptDetailAdapter extends RecyclerView.Adapter<ProfileReceiptDetailAdapter.MyViewHolder> {
//        private Context context;
//        private List<ProfileReceiptDetail> profileReceiptDetailList;
//
//        ProfileReceiptDetailAdapter(Context context, List<ProfileReceiptDetail> profileReceiptDetailList) {//ProfileReceiptDetailAdapter 建構式
//            this.context = context;
//            this.profileReceiptDetailList = profileReceiptDetailList;
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder {//ProfileReceiptDetailAdapter內部類別MyViewHolder
//            TextView date, orderNumber, roomQuantity, roomType, mealsQuantity, meals, result, discounted, totalPrice;
//            Button rating;
//
//
//            MyViewHolder(View itemView) {//MyViewHolder 建構式
//                super(itemView);
//                date = itemView.findViewById(R.id.date);
//                orderNumber = itemView.findViewById(R.id.orderNumber);
//                roomQuantity = itemView.findViewById(R.id.roomQuantity);
//                roomType = itemView.findViewById(R.id.roomType);
//                mealsQuantity = itemView.findViewById(R.id.mealsQuantity);
//                meals = itemView.findViewById(R.id.meals);
//                discounted = itemView.findViewById(R.id.discounted);
//                totalPrice = itemView.findViewById(R.id.totalPrice);
//                result = itemView.findViewById(R.id.result);
//                rating = itemView.findViewById(R.id.rating);
//                result.setOnClickListener(new View.OnClickListener() {//每個itemView 設定監聽事件
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getActivity(), ProfileReceiptDetailResultActivity.class);
//                        Bundle bundle = new Bundle();
//                        Date date = new Date();
//                        //換頁前 準備ProfileReceiptDetail資料等等用bundle將資料帶到下一頁
//                        ProfileReceiptDetail profileReceiptDetail = new ProfileReceiptDetail(order_id, date, date, orderNumber.getText().toString(), roomType.getText().toString(), mealsQuantity.getText().toString(), meals.getText().toString(), discounted.getText().toString(), roomQuantity.getText().toString(), totalPrice.getText().toString(), result.getText().toString());
//                        bundle.putSerializable("profileReceiptDetail", profileReceiptDetail);
//                        intent.putExtras(bundle);
//                        startActivity(intent);//ProfileReceiptListFragment >> ProfileReceiptDetailResultActivity換頁
//
//                    }
//                });
//
//                //去評論
//                rating.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), ProfileAddRatingFragment.class);
//                    String order_id = "15";
//                    Bundle bundle = new Bundle();
//                    ProfileReceiptDetail profileReceiptDetail = new ProfileReceiptDetail(order_id);
//                    bundle.putSerializable("profileReceiptDetail", 15);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    }
//                });
//            }
//        }
//
//        @Override
//        public int getItemCount() {//ProfileReceiptDetailAdapter的方法getItemCount 取得itemView 數量 畫面近來呼叫一次
//            return profileReceiptDetailList.size();
//        }
//
//
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {//ProfileReceiptDetailAdapter的方法onCreateViewHolder 有幾個itemView 就呼叫幾次
//            View itemView = LayoutInflater.from(context).
//                    inflate(R.layout.receipt_item, viewGroup, false);//取得每一個receipt_item準備給產生MyViewHolder時初始化建構式用
//            return new MyViewHolder(itemView);//將產生的MyViewHolder 傳到下一個方法onBindViewHolder使用 準備產生畫面
//
//        }
//
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {//ProfileReceiptDetailAdapter的方法onBindViewHolder 有幾個MyViewHolder 就呼叫幾次
//            final ProfileReceiptDetail profileReceiptDetail = profileReceiptDetailList.get(position);
//            String dateText = dateToSting(profileReceiptDetail.getDate_start()) + " - " +dateToSting(profileReceiptDetail.getDate_end());
//            order_id = profileReceiptDetail.getOrder_id();
//            viewHolder.date.setText(dateText);
//            viewHolder.orderNumber.setText("訂單編號:" + profileReceiptDetail.getOrderNumber());
//            viewHolder.roomQuantity.setText(profileReceiptDetail.getRoomQuantity()+ "間");
//            viewHolder.roomType.setText(profileReceiptDetail.getRoomType());
//            viewHolder.mealsQuantity.setText(profileReceiptDetail.getMealsQuantity().equals("") ? "" : profileReceiptDetail.getMealsQuantity()+ "份");
//            viewHolder.meals.setText(profileReceiptDetail.getMeals().equals("") ? "" : profileReceiptDetail.getMeals());
//            viewHolder.discounted.setText(profileReceiptDetail.getDiscounted().equals("") ? "" : profileReceiptDetail.getDiscounted());
//            viewHolder.result.setText(profileReceiptDetail.getResult());
//            viewHolder.totalPrice.setText("總額NT$" + profileReceiptDetail.getTotal_price());
//
//            if (profileReceiptDetail.getResult().equals("未付款")){
//                viewHolder.rating.setVisibility(View.GONE);
//            }
//
//
//            /*viewHolder.item_view_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ImageView iv = new ImageView(context);
//                    iv.setImageResource(ProfileReceiptDetail.getImage());
//                    Toast toast = new Toast(context);
//                    toast.setView(iv);
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            });*/
//        }
//    }
//
//    private List<ProfileReceiptDetail> getProfileReceiptDetailList() {
//        List<ProfileReceiptDetail> profileReceiptDetailList = new ArrayList<>();
//        Date date = new Date();
//        profileReceiptDetailList.add(new ProfileReceiptDetail("1", date, date, "123456", "海景標準雙人房", "2", "A餐", "享8折優惠", "1" ,"4240", "未付款"));
//        profileReceiptDetailList.add(new ProfileReceiptDetail("2", date, date, "123457", "海景精緻雙人房", "", "", "", "1", "4900", "已付款"));
//        profileReceiptDetailList.add(new ProfileReceiptDetail("3", date, date, "123458", "山景精緻雙人房", "", "", "", "1", "3400", "已付款"));
//
//        return profileReceiptDetailList;
//    }
//
//    //日期轉字串
//    public String dateToSting(Date date){
//        //設定日期格式
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
//        //進行轉換
//        String dateString = sdf.format(date);
//
//        return dateString;
//    }

    private List<OrderRoomDetail> showRoomDetailList() {
        List<OrderRoomDetail> roomDetails = null;
        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/PayDetailServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getRoomPayDetailById");
            jsonObject.addProperty("idCustomer", customerId);
            String jsonOut = jsonObject.toString();
            roomDetailTask = new CommonTask(url, jsonOut);
            try {
                String jsonIn = roomDetailTask.execute().get();
                Type listType = new TypeToken<List<OrderRoomDetail>>() {
                }.getType();
                roomDetails = new Gson().fromJson(jsonIn, listType);
                return roomDetails;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
        return roomDetails;
    }

    private List<OrderInstantDetail> showInstantDetailList() {
        List<OrderInstantDetail> instantDetails = null;
        if (Common.networkConnected(activity)) {
            String url = Common.URL + "/PayDetailServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getInstantPayDetail");
            jsonObject.addProperty("idCustomer", customerId);
            String jsonOut = jsonObject.toString();
            instantDetailTask = new CommonTask(url, jsonOut);
            try {
                String jsonIn = instantDetailTask.execute().get();
                Type listType = new TypeToken<List<OrderInstantDetail>>() {
                }.getType();
                instantDetails = new Gson().fromJson(jsonIn, listType);
                return instantDetails;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(activity, R.string.msg_NoNetwork);
        }
        return instantDetails;
    }

    private void showRefactoredData() {
        List<OrderRoomDetail> details = orderRoomDetails;
        List<OrderInstantDetail> instantDetails = orderInstantDetails;
        HashMap<String, ArrayList<?>> orders = new HashMap<>();
        List<OrderRoomDetail> orderRooms = new ArrayList<>();
        List<OrderInstantDetail> orderInstants = new ArrayList<>();
        if (!details.isEmpty()) {
            for (OrderRoomDetail detailRoom : details) {
                detailContainer.put(detailRoom.getRoomGroup(), null);
            }

            for (OrderRoomDetail detailRoom : details) {
                orderRooms.clear();

                OrderRoomDetail targetRoom = new OrderRoomDetail(detailRoom.getIdRoomReservation(),
                        detailRoom.getRoomGroup(),
                        detailRoom.getCheckInDate(),
                        detailRoom.getCheckOuntDate(),
                        detailRoom.getRoomNumber(),
                        detailRoom.getPrice(),
                        detailRoom.getRoomQuantity(),
                        detailRoom.getRoomTypeName(),
                        detailRoom.getRoomReservationStatus());

                if (detailContainer.get(detailRoom.getRoomGroup()) != null
                        && detailContainer.get(detailRoom.getRoomGroup()).get("roomDetail") != null) {
                    orderRooms = (ArrayList<OrderRoomDetail>) detailContainer.get(detailRoom.getRoomGroup()).get("roomDetail");
                }

                orderRooms.add(targetRoom);
                orders.put("roomDetail", (ArrayList<?>) orderRooms);
                detailContainer.put(detailRoom.getRoomGroup(), orders);
            }

            for (OrderInstantDetail detailInstant : instantDetails) {
                if (detailInstant.getQuantity().equals("0") || detailInstant.getQuantity() == null || Integer.valueOf(detailInstant.getQuantity()) == 0) {
                    break;
                }
                orderInstants.clear();

                OrderInstantDetail targetInstant = new OrderInstantDetail(detailInstant.getDiningTypeName(),
                        detailInstant.getQuantity(),
                        detailInstant.getDtPrice(),
                        detailInstant.getRoomGroup());

                if (detailContainer.get(detailInstant.getRoomGroup()) != null
                        && detailContainer.get(detailInstant.getRoomGroup()).get("instantDetail") != null) {
                    orderInstants = (ArrayList<OrderInstantDetail>) detailContainer.get(detailInstant.getRoomGroup()).get("instantDetail");
                }

                orderInstants.add(targetInstant);
                orders.put("instantDetail", (ArrayList<?>) orderInstants);
                detailContainer.put(detailInstant.getRoomGroup(), orders);
            }
        }
    }
}



