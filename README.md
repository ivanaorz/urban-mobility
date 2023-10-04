Project name:Urban Mobility

Purpose of the project: 
1. To create unit, integration and end-to-end testing;
2. To automate building process using Jenkins;
3. To create two pipelines (one for development and one for production).

Setting up the development pipeline:
- Press 'New Item'.

![Screenshot 2023-10-01 100513](https://github.com/ivanaorz/urban-mobility/assets/113606494/b0410dd1-1730-40d1-a824-5b64cd4a193a)
- Enter item name.
- Choose 'Freestyle project' and press 'OK'.
 ![Screenshot 2023-10-01 103117](https://github.com/ivanaorz/urban-mobility/assets/113606494/651e09fb-2fb4-4d97-9fe3-75825c31c0e8)
  
- Choose 'Git'.
- Enter repository URL.
- Enter your credentials (GitHub username). 
![Screenshot 2023-10-01 103514](https://github.com/ivanaorz/urban-mobility/assets/113606494/04ab4533-270a-4c60-8202-d19550a3b09c)

  
-Enter the development environment branch name. 
![Screenshot 2023-10-01 104003](https://github.com/ivanaorz/urban-mobility/assets/113606494/7da058eb-5337-4c53-9e0c-98596cb7786e)


-In 'Branch to merge to' enter the name of the development environment branch.
![Screenshot 2023-10-01 104858](https://github.com/ivanaorz/urban-mobility/assets/113606494/f4ac12c3-34b8-48f4-bd3f-cb46c9b18683)

  
-In 'Build Steps' choose 'Execute Windows batch command' (if you use Windows).

-In the 'command' field enter the following commands that will run every time Jenkins will build the project:
![Screenshot 2023-10-01 105004](https://github.com/ivanaorz/urban-mobility/assets/113606494/53e3b115-fc63-4f48-a362-ec155ecbf644)

-In 'Post-build actions' choose 'Archive the artifacts'.

-Enter the name of type of artifacts to be archived.

-Also choose 'JUnit test result report'.

-Enter the path to the 'Maven Surefire Test Report XML Files'.
![Screenshot 2023-10-01 110001](https://github.com/ivanaorz/urban-mobility/assets/113606494/64d43c3c-ffe1-4885-adfa-1a8f602aeef0)

-Press 'save' and the development pipeline set up is ready!!!


Setting up the production pipeline:
- Press 'New Item'.

![Screenshot 2023-10-01 100513](https://github.com/ivanaorz/urban-mobility/assets/113606494/b0410dd1-1730-40d1-a824-5b64cd4a193a)
- Enter item name.
- Choose 'Freestyle project' and press 'OK'.
![Screenshot 2023-10-02 020034](https://github.com/ivanaorz/urban-mobility/assets/113606494/12e3e235-44a8-4b6b-8e07-6a71a1b51539)

- Choose 'Git'.
- Enter repository URL.
- Enter the production environment branch name (in my case it is */master)
![image](https://github.com/ivanaorz/urban-mobility/assets/113606494/4fd11ce8-fe9b-4588-b164-2a7ef79ad766)

![Screenshot 2023-10-04 112723](https://github.com/ivanaorz/urban-mobility/assets/113606494/b005e4ff-cdc6-4333-b67c-6653ba893780)

![Screenshot 2023-10-04 112813](https://github.com/ivanaorz/urban-mobility/assets/113606494/109bc841-957a-4123-b7cb-a0b5ec1eacba)








  




  


