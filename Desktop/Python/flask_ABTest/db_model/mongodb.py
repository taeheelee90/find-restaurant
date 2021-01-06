import pymongo

MONGO_HOST = 'localhost'
MONGCON_CONN = pymongo.MongoClient('mongodb://%s' % (MONGO_HOST))

def conn_mongodb():
    try:
        MONGCON_CONN.admin.command('ismaster')
        blog_ab = MONGCON_CONN.blog_session_db.blog_ab
    except:
        MONGO_CONN = pymongo.MongoClient('mongodb://%s' % (MONGO_HOST))
        blog_ab = MONGO_CONN.blog_session_db.blog_ab
    return blog_ab