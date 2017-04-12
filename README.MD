#### 安装Git

Window使用Cmder，自带

#### 创建版本库

```shell
git init
git add readme.txt
git commit -m "add 3 files."
git status
```

#### 时光机穿梭

```shell
git diff readme.txt 
```

#### 版本回退

#### 工作区和暂存区

#### 管理修改

#### 撤销修改

#### 删除文件

#### 远程仓库

第1步：创建SSH Key。在用户主目录下，看看有没有.ssh目录，如果有，再看看这个目录下有没有`id_rsa`和`id_rsa.pub`这两个文件，如果已经有了，可直接跳到下一步。如果没有，打开Shell（Windows下打开Git Bash），创建SSH Key：

```shell
ssh-keygen -t rsa -C "youremail@example.com"
```

第2步：登陆GitHub，打开“Account settings”，“SSH Keys”页面：

然后，点“Add SSH Key”，填上任意Title，在Key文本框里粘贴`id_rsa.pub`文件的内容：

![github-addkey-1](http://www.liaoxuefeng.com/files/attachments/001384908342205cc1234dfe1b541ff88b90b44b30360da000/0)

点“Add Key”，你就应该看到已经添加的Key：

![github-addkey-2](http://www.liaoxuefeng.com/files/attachments/0013849083502905a4caa2dc6984acd8e39aa5ae5ad6c83000/0)

#### 添加远程库

```shell
git remote add origin git@github.com:michaelliao/learngit.git
git push -u origin master
git push origin master
```

#### 从远程库克隆

```shell
git clone git@github.com:michaelliao/gitskills.git
```