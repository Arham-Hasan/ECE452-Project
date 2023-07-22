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
    let response = {"data":{"data":[]}}
    try {
    response = await axios.get(
		`https://graph.facebook.com/v17.0/${TRACKTORAPP_HASHTAG}/recent_media?user_id=${USER_ID}&fields=media_url%2Ccaption%2Ctimestamp%2Cmedia_type&access_token=${ACCESS_TOKEN}`
	);
    }catch(error){
        console.log(error)
        response= {"data":{"data":[]}}
    }
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

// (async() => {
//     await db_stuff();
//   })();
// function get_instagram_info()

// setInterval(function() {
//     // your code goes here...
// }, 60 * 1000 * 10);
