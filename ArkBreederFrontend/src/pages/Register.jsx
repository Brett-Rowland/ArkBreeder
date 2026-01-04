import { useState } from "react";
import { useNavigate } from "react-router";

export default function Register(){
    const navigate = useNavigate();

    const [loginCredentials, setLoginCredentials] = useState({
        "username":"",
        "password":""
        // "verifyPassword":""
    })



    const changeLoginCreds = (e) => {
        console.log(loginCredentials)
        setLoginCredentials({
            ...loginCredentials, [e.target.name]:e.target.value}
        )
    }


    const createAccount = (e) => {
        e.preventDefault();

        fetch("http://localhost:8787/users/create",{
            method : "POST",
            headers : {
                "Content-Type":"application/json"
            },
            body : JSON.stringify(loginCredentials)
        })
        .then((res) => {
            if (res.status == 200){
                return res.json()
            }

            else if (res.status == 226){
                throw new Error("Username is Taken")
            }
            else{
                throw new Error("Internal Server Error")
            }


        })
        .then(data =>{
            sessionStorage.setItem("token", JSON.stringify(data))
            navigate("/breeding-lines")
        })
        .catch(err => {
            console.error("Error: " + err)
        })

    }


    return(

        <>
        <div className="createAccountForm">
            
            <form onSubmit={createAccount}>
            <header>
                Create an Account
            </header>

            <label>Enter a Username: 
                <input required name="username" onChange={changeLoginCreds} type="text" />
            </label>
            <br/>
            <label >Enter a Password:
                <input required type="password" onChange={changeLoginCreds} name="password" />
            </label>
            {/* <br/>
            <label>
                Verify Password:
                <input required type="password" onChange={changeLoginCreds} name="verifyPassword"/>
            </label> */}
                <br/>
                <button>Create Account!</button>
            </form>
        </div>
        </>
    )


}