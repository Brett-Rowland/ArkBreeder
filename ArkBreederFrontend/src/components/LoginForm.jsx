export default function LoginForm({ handleLogin, changeUserDetails}) {
  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      <div className="w-full max-w-sm rounded-2xl bg-white p-8 shadow-xl">
        <h2 className="mb-6 text-center text-2xl font-semibold text-gray-800">
          Sign In
        </h2>

        <form onSubmit={handleLogin} method="post" className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Username</label>
            <input
              required
              name="username"
              onChange={changeUserDetails}
              type="text"
              className="mt-1 w-full rounded-lg border border-gray-300 p-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Password</label>
            <input
              required
              name="password"
              onChange={changeUserDetails}
              type="password"
              className="mt-1 w-full rounded-lg border border-gray-300 p-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
            />
          </div>

          <button
            type="submit"
            className="w-full rounded-lg bg-indigo-600 p-2 text-white font-medium hover:bg-indigo-700 transition-colors"
          >
            Login
          </button>
        </form>

        {/* Optional forgot password link */}
        {/* <p className="mt-3 text-center text-sm text-indigo-600 hover:underline cursor-pointer">
          Forgot Password?
        </p> */}
        <a href="/registar">
        <button
          type="button"
          className="mt-4 w-full rounded-lg border border-indigo-600 p-2 text-indigo-600 font-medium hover:bg-indigo-50 transition-colors"
        >
          Create an Account
        </button>
        </a>
      </div>
    </div>
  );
}
