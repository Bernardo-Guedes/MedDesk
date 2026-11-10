
import {
  House,
  User,
  UserShield,
  FilePlusCorner,
  ClipboardList,
} from "lucide-react";

import { NavLink } from "react-router";

function Sidebar() {
  const links = [
    { to: "/admin", label: "Início", icon: House, end: true },
    { to: "/admin/pacientes", label: "Pacientes", icon: User },
    { to: "/admin/profissionais", label: "Profissionais", icon: UserShield },
    { to: "/admin/internacoes", label: "Internações", icon: ClipboardList },
    {
      to: "/admin/atendimentos/novo",
      label: "Criar Atendimento",
      icon: FilePlusCorner,
    },
  ];

  return (
    <aside className="h-full">
      <div className="bg-superficie p-5 h-full overflow-y-auto">
        <ul className="flex flex-col gap-3 text-lg font-semibold">
          {links.map(({ to, label, icon: Icon, end }) => (
            <li key={to}>
              <NavLink
                to={to}
                end={end}
                className={({ isActive }) =>
                  `flex items-center gap-2 rounded-lg p-3 transition-colors ${
                    isActive
                      ? "bg-blue-100 text-blue-700"
                      : "text-gray-600 hover:bg-gray-100"
                  }`
                }
              >
                <Icon size={20} />
                <span>{label}</span>
              </NavLink>
            </li>
          ))}
        </ul>
      </div>
    </aside>
  );
}

export default Sidebar;
