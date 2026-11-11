import { Routes, Route } from "react-router";

import MainLayout from "./layouts/MainLayout";

import Home from "./pages/admin/Home";
import Pacientes from "./pages/admin/Pacientes";
import Profissionais from "./pages/admin/Profissionais";
import Internacoes from "./pages/admin/Internacoes";
import CriarAtendimento from "./pages/admin/CriarAtendimento";
import DetalhesConsulta from "./pages/admin/DetalhesConsulta";
import DetalhesPaciente from "./pages/admin/DetalhesPaciente";
import DetalhesQuarto from "./pages/admin/DetalhesQuarto";
import HomeProfissional from "./pages/profissionalSaude/Home";
import PerfilProfissional from "./pages/profissionalSaude/Perfil";
import Agenda from "./pages/profissionalSaude/Agenda";
import NovaConsulta from "./pages/profissionalSaude/NovaConsulta";

function App() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/admin/" element={<Home />} />
        <Route path="/admin/pacientes" element={<Pacientes />} />
        <Route
          path="/admin/pacientes/1"
          element={<DetalhesPaciente />}
        />
        <Route path="/admin/profissionais" element={<Profissionais />} />
        <Route path="/admin/internacoes" element={<Internacoes />} />
        <Route path="/admin/internacoes/quartos/:numeroQuarto" element={<DetalhesQuarto />} />
        <Route
          path="/admin/atendimentos/novo"
          element={<CriarAtendimento />}
        />
        <Route
          path="/admin/consultas/:consultaId"
          element={<DetalhesConsulta />}
        />
      </Route>
      <Route element={<MainLayout profissional />}>
        <Route path="/profissional-saude" element={<HomeProfissional />} />
        <Route path="/profissional-saude/perfil" element={<PerfilProfissional />} />
        <Route path="/profissional-saude/agenda" element={<Agenda />} />
        <Route path="/profissional-saude/nova-consulta" element={<NovaConsulta />} />
        <Route path="/profissional-saude/consultas/:consultaId" element={<DetalhesConsulta />} />
      </Route>
    </Routes>
  );
}

export default App;