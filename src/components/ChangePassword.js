import { useState } from "react";
import axios from "axios";

export default function ChangePassword() {
  const [currentWindow, setCurrentWindow] = useState("email");
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const API_BASE_URL = "http://localhost:8080/api/forgot-password";

  const handleSendOTP = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      const response = await axios.post(`${API_BASE_URL}/send-otp`, {
        email: email,
      });

      if (response.data.success) {
        setSuccess("OTP sent to your email successfully!");
        setCurrentWindow("verification");
      } else {  
        setError(response.data.message || "Failed to send OTP");
      }
    } catch (err) {
      if (err.response && err.response.data) {
        setError(err.response.data.message || "Failed to send OTP");
      } else {
        setError("Network error. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleVerifyOTP = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await axios.post(`${API_BASE_URL}/verify-otp`, {
        email: email,
        otp: otp,
      });

      if (response.data.success) {
        setSuccess("OTP verified successfully!");
        setCurrentWindow("changePassword");
      } else {
        setError(response.data.message || "Invalid OTP code");
      }
    } catch (err) {
      if (err.response && err.response.data) {
        setError(err.response.data.message || "Invalid OTP code");
      } else {
        setError("Network error. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    if (newPassword !== confirmPassword) {
      setError("Passwords do not match");
      setLoading(false);
      return;
    }

    if (newPassword.length < 6) {
      setError("Password must be at least 6 characters long");
      setLoading(false);
      return;
    }

    try {
      const response = await axios.post(`${API_BASE_URL}/reset-password`, {
        email: email,
        otp: otp,
        newPassword: newPassword,
      });

      if (response.data.success) {
        setSuccess("Password changed successfully!");
        setTimeout(() => {
          resetForm();
        }, 2000);
      } else {
        setError(response.data.message || "Failed to change password");
      }
    } catch (err) {
      if (err.response && err.response.data) {
        setError(err.response.data.message || "Failed to change password");
      } else {
        setError("Network error. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setCurrentWindow("email");
    setEmail("");
    setOtp("");
    setNewPassword("");
    setConfirmPassword("");
    setError("");
    setSuccess("");
    setShowPassword(false);
    setShowConfirmPassword(false);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 py-4 px-4">
      <div className="w-full max-w-md bg-white rounded-lg shadow-lg overflow-hidden">
        {/* Header */}
        <div className="text-center bg-white pt-6 px-6">
          <div className="mx-auto rounded-full bg-gray-100 flex items-center justify-center mb-4 h-16 w-16">
            <span className="font-bold text-gray-700">
              {currentWindow === "email" && "Email"}
              {currentWindow === "verification" && "OTP"}
              {currentWindow === "changePassword" && "Reset"}
            </span>
          </div>

          <h2 className="text-2xl font-bold mb-1">
            {currentWindow === "email" && "Forgot Password"}
            {currentWindow === "verification" && "Verify OTP"}
            {currentWindow === "changePassword" && "Change Password"}
          </h2>

          <p className="text-gray-500 mb-6">
            {currentWindow === "email" && "Enter your email address to receive OTP"}
            {currentWindow === "verification" && "Enter the OTP code sent to your email"}
            {currentWindow === "changePassword" && "Enter your new password"}
          </p>
        </div>

        <div className="px-6 pb-6">
          {/* Error Alert */}
          {error && (
            <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded mb-4">
              {error}
            </div>
          )}

          {/* Success Alert */}
          {success && (
            <div className="bg-green-50 border border-green-200 text-green-700 px-4 py-3 rounded mb-4">
              {success}
            </div>
          )}

          {/* Window 1: Email Input */}
          {currentWindow === "email" && (
            <form onSubmit={handleSendOTP} className="space-y-4">
              <div>
                <label htmlFor="email" className="block text-gray-700 font-medium mb-2">
                  Email Address
                </label>
                <input
                  type="email"
                  id="email"
                  placeholder="Enter your email address"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
              </div>
              <button 
                type="submit" 
                className={`w-full py-2 px-4 rounded-md font-medium text-white ${
                  loading ? "bg-blue-400 cursor-not-allowed" : "bg-blue-600 hover:bg-blue-700"
                }`}
                disabled={loading}
              >
                {loading ? "Sending OTP..." : "Send OTP"}
              </button>
            </form>
          )}

          {/* Window 2: OTP Verification */}
          {currentWindow === "verification" && (
            <form onSubmit={handleVerifyOTP} className="space-y-4">
              <div>
                <label htmlFor="otp" className="block text-gray-700 font-medium mb-2">
                  Enter OTP Code
                </label>
                <input
                  type="text"
                  id="otp"
                  placeholder="Enter 6-digit OTP"
                  value={otp}
                  onChange={(e) => setOtp(e.target.value.replace(/\D/g, "").slice(0, 6))}
                  maxLength={6}
                  required
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-center text-lg tracking-widest"
                />
                <p className="text-gray-500 text-sm text-center mt-2">
                  OTP sent to: <span className="font-medium">{email}</span>
                </p>
              </div>
              <div className="flex gap-2">
                <button 
                  type="button" 
                  className="flex-1 py-2 px-4 border border-gray-300 rounded-md font-medium text-gray-700 hover:bg-gray-50"
                  onClick={() => setCurrentWindow("email")}
                >
                  Back
                </button>
                <button 
                  type="submit" 
                  className={`flex-1 py-2 px-4 rounded-md font-medium text-white ${
                    loading || otp.length !== 6 
                      ? "bg-blue-400 cursor-not-allowed" 
                      : "bg-blue-600 hover:bg-blue-700"
                  }`}
                  disabled={loading || otp.length !== 6}
                >
                  {loading ? "Verifying..." : "Verify OTP"}
                </button>
              </div>
            </form>
          )}

          {/* Window 3: Change Password */}
          {currentWindow === "changePassword" && (
            <form onSubmit={handleChangePassword} className="space-y-4">
              <div>
                <label htmlFor="newPassword" className="block text-gray-700 font-medium mb-2">
                  New Password
                </label>
                <div className="relative">
                  <input
                    type={showPassword ? "text" : "password"}
                    id="newPassword"
                    placeholder="Enter new password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    required
                    className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 pr-12"
                  />
                  <button
                    type="button"
                    className="absolute inset-y-0 right-0 px-3 flex items-center text-gray-500 hover:text-gray-700"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? "Hide" : "Show"}
                  </button>
                </div>
              </div>

              <div>
                <label htmlFor="confirmPassword" className="block text-gray-700 font-medium mb-2">
                  Confirm New Password
                </label>
                <div className="relative">
                  <input
                    type={showConfirmPassword ? "text" : "password"}
                    id="confirmPassword"
                    placeholder="Confirm new password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required
                    className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 pr-12"
                  />
                  <button
                    type="button"
                    className="absolute inset-y-0 right-0 px-3 flex items-center text-gray-500 hover:text-gray-700"
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                  >
                    {showConfirmPassword ? "Hide" : "Show"}
                  </button>
                </div>
              </div>

              <div className="flex gap-2 pt-2">
                <button
                  type="button"
                  className="flex-1 py-2 px-4 border border-gray-300 rounded-md font-medium text-gray-700 hover:bg-gray-50"
                  onClick={() => setCurrentWindow("verification")}
                >
                  Back
                </button>
                <button 
                  type="submit" 
                  className={`flex-1 py-2 px-4 rounded-md font-medium text-white ${
                    loading ? "bg-blue-400 cursor-not-allowed" : "bg-blue-600 hover:bg-blue-700"
                  }`}
                  disabled={loading}
                >
                  {loading ? "Changing..." : "Change Password"}
                </button>
              </div>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}