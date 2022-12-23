# 博客园网页端接口

本文接口从接口工厂中获取`WebApiProvider`实例。所有接口均通过解析HTML网页所得。

# 用户接口 `IUserApi`

| 接口名称         | 方法名称                                            |
|--------------|-------------------------------------------------|
| 获取登录用户信息     | userInfo()                                      |
| 获取登录用户个人资料   | profile()                                       |
| 获取登录账号信息     | accountInfo()                                   |
| 上传头像         | uploadAvatar(body:文件)                           |
| 检查昵称是否可用     | checkDisplayName(displayName:昵称)                |
| 更新昵称         | updateDisplayName(displayName:昵称)               |
| 获取关注列表       | followers(blogApp,page)                         |
| 获取粉丝列表       | fans(blogApp, page)                             |
| 加关注          | follow(userId:用户ID, remark:备注)                  |
| 是否关注         | isFollow(userId:用户Id)                           |
| 取消关注         | unfollow(userId:用户ID, isRemoveGroup:默认为True)    |
| 获取个人资料中的所有字段 | introFields()                                   |
| 更新个人资料       | updateIntro(map:上面introFields接口获取的字段，需要更新哪个改哪个) |

# 公共接口 `ICommonApi`

> 公共定义接口

| 接口名称       | 方法名称                              |
|------------|-----------------------------------|
| 博客分类列表   | categoryList()                        |

> 上传文件相关

| 接口名称               | 方法名称                                                              |
|--------------------|-------------------------------------------------------------------|
| \[1/3\]上传文件初始化     | prepareUploadFile()                                               |
| \[2/3\]获取上传文件Token | requestUploadFileToken(fileName, size:文件长度)                       |
| \[3/3\]开始上传文件      | uploadFile(token:上传Token,contentType:MIME文件类型(image/png),file:文件) |

# 博客接口 `IBlogApi`

> 文章相关

| 接口名称           | 方法名称                                          |
|----------------|-----------------------------------------------|
| 博客列表           | blogList(param:请求参数，字段详细看注释)                  |
| 博客文章内容         | content(url:原文路径)                             |
| 获取当前文章的上下篇文章   | nextArticle(blogApp, postId)                  |
| 根据当前文章推荐博客     | recommendBlogs(itemId: postId, itemTitle: 标题) |
| 文章的顶、踩、评论、阅读数量 | postStat(blogApp, body:格式：\[id1,id2\])        |
| 作者的博客排行榜       | authorTopList(blogApp)                        |

> 评论相关

| 接口名称           | 方法名称                                          |
|----------------|-----------------------------------------------|
| 博客列表           | blogList(param:请求参数，字段详细看注释)                  |

# 排行榜接口 `IRankingApi`

| 接口名称   | 方法名称                         |
|--------|------------------------------|
| 首页的博客排行，其中包含：48小时阅读排行、10天推荐排行、48小时评论排行、编辑推荐   | liteBlogRanking() |
| 首页的新闻排行，其中包含：推荐新闻、热门新闻 | liteNewsRanking()            |

# 搜索接口 `ISearchApi`

| 接口名称   | 方法名称                         |
|--------|------------------------------|
|  搜索博客  | searchBlogs(keyword,page) |
|  搜索我的博客  | searchMyBlogs(keyword,page) |
|  搜索新闻  | searchNews(keyword,page) |
|  搜索博问  | searchQuestions(keyword,page) |
|  搜索知识库  | searchKbs(keyword,page) |
|  搜索用户  | searchUsers(keyword,page) |