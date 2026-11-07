import { Routes, Route } from "react-router";

import MainLayout from "./layouts/MainLayout";

import Home from "./pages/admin/Home";
import Pacientes from "./pages/admin/Pacientes";
import Profissionais from "./pages/admin/Profissionais";
import Internacoes from "./pages/admin/Internacoes";
import CriarAtendimento from "./pages/admin/CriarAtendimento";

function App() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/admin/" element={<Home />} />
        <Route path="/admin/pacientes" element={<Pacientes />} />
        <Route path="/admin/profissionais" element={<Profissionais />} />
        <Route path="/admin/internacoes" element={<Internacoes />} />
        <Route
          path="/admin/atendimentos/novo"
          element={<CriarAtendimento />}
        />
      </Route>
    </Routes>
  );
}

export default App;