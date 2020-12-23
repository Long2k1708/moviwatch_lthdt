package com.princefrog2k.lthdt.moviwatch.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.princefrog2k.lthdt.moviwatch.data.FilmItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseDatabaseFetcher {

    public interface DataFetchInterface {
        void onGetData();

        void onGetDataFinish(List<FilmItem> dataGetted);
    }


    public interface GetSingleDataFromFirebase {
        void onGetDone(List dataGetted);

        void onGetError();
    }

    public static void getAllDataFromFirebase(final String childName, final DataFetchInterface getData) {
        List<FilmItem> listWhileGettingData = new ArrayList<FilmItem>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(childName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listWhileGettingData.add(dataSnapshot.getValue(FilmItem.class));
                getData.onGetData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbRef.child(childName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == listWhileGettingData.size()) {
                    getData.onGetDataFinish(listWhileGettingData);
                    Log.w("Number Of Docs: ", String.valueOf(dataSnapshot.getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getTopNewest(String childName, DataFetchInterface getData) {
        List<FilmItem> listWhileGettingData = new ArrayList<FilmItem>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(childName).limitToLast(3).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listWhileGettingData.add(dataSnapshot.getValue(FilmItem.class));
                getData.onGetData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbRef.child(childName).limitToLast(3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == listWhileGettingData.size())
                    getData.onGetDataFinish(listWhileGettingData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getTopNewestByCategory(String childName, String category, int limit, DataFetchInterface getData) {
        List<FilmItem> listWhileGettingData = new ArrayList<FilmItem>();
        List<FilmItem> listResult = new ArrayList<FilmItem>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(childName).limitToFirst(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FilmItem fi = dataSnapshot.getValue(FilmItem.class);
                listWhileGettingData.add(dataSnapshot.getValue(FilmItem.class));
                if (fi.getCategory().contains(category) && listResult.size() < limit)
                    listResult.add(fi);
                getData.onGetData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRef.child(childName).limitToFirst(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == listWhileGettingData.size())
                    getData.onGetDataFinish(listResult);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getTopNewestByCategory(String childName, String category, DataFetchInterface getData) {
        List<FilmItem> listWhileGettingData = new ArrayList<FilmItem>();
        List<FilmItem> listResult = new ArrayList<FilmItem>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(childName).limitToFirst(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FilmItem fi = dataSnapshot.getValue(FilmItem.class);
                listWhileGettingData.add(dataSnapshot.getValue(FilmItem.class));
                if (fi.getCategory().contains(category))
                    listResult.add(fi);
                getData.onGetData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRef.child(childName).limitToFirst(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == listWhileGettingData.size())
                    getData.onGetDataFinish(listResult);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getTopTrending(String childName, int limit, DataFetchInterface getData) {
        List<FilmItem> listWhileGettingData = new ArrayList<FilmItem>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(childName).orderByChild("viewCount").limitToLast(limit).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listWhileGettingData.add(dataSnapshot.getValue(FilmItem.class));
                getData.onGetData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbRef.child(childName).orderByChild("viewCount").limitToLast(limit).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == listWhileGettingData.size()) {
                    Collections.reverse(listWhileGettingData);
                    getData.onGetDataFinish(listWhileGettingData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getSingleData(String nodeData, final GetSingleDataFromFirebase getData) {
        final List data = new ArrayList();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(nodeData).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.add(dataSnapshot.getValue());
                getData.onGetDone(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                getData.onGetError();
            }
        });
    }
}
