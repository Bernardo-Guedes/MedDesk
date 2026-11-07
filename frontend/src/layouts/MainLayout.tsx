import { Outlet } from "react-router";
import Sidebar from "../components/Sidebar";
import Header from "../components/Header";

function MainLayout() {
  return (
    <div className="min-h-screen bg-background">
      <Header />
      <main className="flex">
        <Sidebar />
        <div className="flex-1">
            <Outlet />
        </div>
      </main>
    </div>
  );
}

export default MainLayout;
