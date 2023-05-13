class User {
    id;
    firstName;
    lastName;
    gender;
    birth;
    email;
    password;
    cartUser;

	constructor (id,firstName, lastName, gender, birth, email, password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.password = password;
        // this.cartUser = cartUser;
	}

    // setCartUser(cartUser) {
    //     this.cartUser = cartUser;
    // }

    // getCartUser() {
    //     return this.cartUser;
    // }

    setID(id) {
        this.id = id;
    }

    getID() {
        return this.id;
    }
    
    getFirstName() {
        return this.firstName;
    }

    setFirstName(firstName) {
        this.firstName = firstName;
    }

    getLastName() {
        return this.lastName;
    }

    setLastName(lastName) {
        this.lastName = lastName;
    }

    getGender() {
        return this.gender;
    }

    setGender(gender) {
        this.gender = gender;
    }

    getBirth() {
        return this.birth;
    }

    setBirth(birth) {
        this.birth = birth;
    }

    getEmail() {
        return this.email;
    }

    setEmail(email) {
        this.email = email;
    }

    getPassword() {
        return this.password;
    }

    setPassword(password) {
        this.password = password;
    }
}