require('dotenv').config()
const express=require("express");

const axios = require('axios');
const body_parser = require("body-parser");
const { initializeApp, applicationDefault, cert } = require('firebase-admin/app');
const { getFirestore, Timestamp, FieldValue, Filter } = require('firebase-admin/firestore');

const app = express().use(body_parser.json());
const port = process.env.PORT || 3000;

var admin = require("firebase-admin");

const serviceAccount = require('./tracktor-a01ee-firebase-adminsdk-hk62p-50ee2f6b6f.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
  });


const db = getFirestore();

async function get_checked_posts(){
    const doc = await db.collection('instagram').doc('ids').get();
    const checked = doc.data()["checked"]
    return checked;
}

async function add_to_db(ids, fullcircle_posts, kitchener_posts){
    const db_ids = db.collection("instagram").doc("ids");
    const db_fullcircle_posts = db.collection("instagram").doc("fullcirclefoods");
    const db_kitchener_posts = db.collection("instagram").doc("kitchenermarket");

    for(const id of ids){
        db_ids.update({
            ["checked"]:admin.firestore.FieldValue.arrayUnion(id)
        })
    }
    for(const elem of fullcircle_posts){
        db_fullcircle_posts.update({
            ["posts"]:admin.firestore.FieldValue.arrayUnion(elem)
        })
    }
    for(const elem of kitchener_posts){
        db_kitchener_posts.update({
            ["posts"]:admin.firestore.FieldValue.arrayUnion(elem)
        })
    }
    
    


}

async function check_hashtags(checked){
    const USER_ID = process.env.TRACKTOR_USER_ID;
    const ACCESS_TOKEN = process.env.ACCESS_TOKEN;
    const TRACKTORAPP_HASHTAG = process.env.TRACKTORAPP_HASHTAG;
    const KITCHENERMARKET_HASHTAG = process.env.KITCHENERMARKET_HASHTAG;
    const FULLCIRCLEFOODS_HASHTAG = process.env.FULLCIRCLEFOODS_HASHTAG;
    console.log("pre insta")
    //console.log(`https://graph.facebook.com/v17.0/${TRACKTORAPP_HASHTAG}/recent_media?user_id=${USER_ID}&fields=media_url%2Ccaption%2Ctimestamp%2Cmedia_type&access_token=${ACCESS_TOKEN}`)
    const response = await axios.get(
        `https://graph.facebook.com/v17.0/${TRACKTORAPP_HASHTAG}/recent_media?user_id=${USER_ID}&fields=media_url%2Ccaption%2Ctimestamp%2Cmedia_type&access_token=${ACCESS_TOKEN}`
    );
    console.log("post insta")
    const data= response["data"]["data"]
    let kitchener_posts = [];
    let fullcircle_posts = [];
    let ids = [];

    for (const post of data){
        if(checked.includes(post.id)){
            continue;
        }
        if (post.caption.toLowerCase().indexOf("#kitchenermarket") >= 0){
            if(post.media_type == "IMAGE"){
                kitchener_posts.push({
                    "image_url": post.media_url,
                    "time_stamp": post.timestamp
                });
                ids.push(post.id);
            }
            
        }
        else if (post.caption.toLowerCase().indexOf("#fullcirclefoods") >= 0){
            if(post.media_type == "IMAGE"){
                fullcircle_posts.push({
                    "image_url": post.media_url,
                    "time_stamp": post.timestamp
                })
            }
            ids.push(post.id);
        }
    }
    return [ids,kitchener_posts,fullcircle_posts]
};

async function instagram() {
    const checked = await get_checked_posts();
    const to_add = await check_hashtags(checked);
    ids=to_add[0]
    kitchener_posts=to_add[1]
    fullcircle_posts=to_add[2]
    await add_to_db(ids,fullcircle_posts,kitchener_posts)
    console.log("just finished")
};



app.get('/', (req, res) => {
    console.log(`loaded page`)
    res.send('Hello World!')
})
  
app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})

instagram()
setInterval(instagram,60*1000*3)



[
    {
        "image_url":"https://scontent.cdninstagram.com/v/t51.29350-15/361908566_656604863021462_5379703750619264285_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=8ae9d6&_nc_ohc=ciGaUHgiJ74AX9Dl8x7&_nc_ht=scontent.cdninstagram.com&edm=AEoDcc0EAAAA&oh=00_AfCEF8obNvP-qtjxutVdPY0h4frVhYpDrlVQZLiuvQC2_g&oe=64C00978",
        "time_stamp":"2023-07-21T07:54:57+0000"
    },
    {
        "image_url":"https://scontent.cdninstagram.com/v/t51.29350-15/361776319_242952821873744_1113187544652349644_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=8ae9d6&_nc_ohc=4cT1xpPsQ40AX9RQAM8&_nc_oc=AQkAjCWqcb3hzjtfEv_kzaBhkusK_WP51njHITptXx1OaCoU62eWD7mon7QGo4uNjjgBg9XMWcaPZNEjSGWw1cOz&_nc_ht=scontent.cdninstagram.com&edm=AEoDcc0EAAAA&oh=00_AfDIClVSGVe37LVVtKWy23j0RqkiWnGqgLK5TrcFVS0H-Q&oe=64BF08D9",
        "time_stamp":"2023-07-21T07:48:29+0000"
    },
    {
        "image_url":"https://scontent.cdninstagram.com/v/t51.29350-15/361744504_686772926627081_7696414488619951809_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=8ae9d6&_nc_ohc=OfD94zWPQOAAX8w1Cgv&_nc_oc=AQm-ysNXzm_Gz8uCdqf4giEqRdNhuG1vm4q14WD3YGCwfgqTybrQ-c8ymcZZH1WXcgt7yeAxmS_YjVAspi2yNBnm&_nc_ht=scontent.cdninstagram.com&edm=AEoDcc0EAAAA&oh=00_AfBVDO51qToNIiLRUZar7-4IjIR502e6WIxGeiEFNJnRVg&oe=64BFB729",
        
        "time_stamp":"2023-07-21T07:55:29+0000"
    }
]



