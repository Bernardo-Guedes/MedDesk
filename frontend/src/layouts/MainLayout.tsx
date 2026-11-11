import { Outlet } from "react-router";
import Sidebar from "../components/Sidebar";
import Header from "../components/Header";

function MainLayout({ profissional = false }: { profissional?: boolean }) {
  return (
    <div className="flex h-screen flex-col bg-background">
      <Header profissional={profissional} />
      <main className="flex min-h-0 flex-1">
        <Sidebar profissional={profissional} />
        <div className="min-w-0 flex-1 overflow-y-auto">
            <Outlet />
        </div>
      </main>
    </div>
  );
}

export default MainLayout;
