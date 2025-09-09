import axios from "axios";

// ⚠️ For now we hardcode login as "admin"
// Later, you can replace this with dynamic login (JWT recommended)
const api = axios.create({
  baseURL: "http://localhost:8080/api",
  auth: {
    username: "admin", // 👈 replace with your actual admin username
    password: "admin123", // 👈 replace with the actual password
  },
});

export default api;
