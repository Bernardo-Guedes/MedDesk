import { Routes, Route } from "react-router";
import { useEffect, type ReactNode } from "react";

import MainLayout from "./layouts/MainLayout";

import Home from "./pages/admin/Home";
import Pacientes from "./pages/admin/Pacientes";
import Profissionais from "./pages/admin/Profissionais";
import Internacoes from "./pages/admin/Internacoes";
import Atendimentos from "./pages/admin/Atendimentos";
import CriarAtendimento from "./pages/admin/CriarAtendimento";
import DetalhesConsulta from "./pages/admin/DetalhesConsulta";
import DetalhesPaciente from "./pages/admin/DetalhesPaciente";
import DetalhesQuarto from "./pages/admin/DetalhesQuarto";
import HomeProfissional from "./pages/profissionalSaude/Home";
import PerfilProfissional from "./pages/profissionalSaude/Perfil";
import Agenda from "./pages/profissionalSaude/Agenda";
import NovaConsulta from "./pages/profissionalSaude/NovaConsulta";

function Pagina({ titulo, children }: { titulo: string; children: ReactNode }) {
  useEffect(() => {
    document.title = `MedDesk | ${titulo}`;
  }, [titulo]);

  return children;
}

function App() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/admin/" element={<Pagina titulo="Dashboard"><Home /></Pagina>} />
        <Route path="/admin/pacientes" element={<Pagina titulo="Pacientes"><Pacientes /></Pagina>} />
        <Route
          path="/admin/pacientes/1"
          element={<Pagina titulo="Detalhes do Paciente"><DetalhesPaciente /></Pagina>}
        />
        <Route path="/admin/profissionais" element={<Pagina titulo="Profissionais"><Profissionais /></Pagina>} />
        <Route path="/admin/internacoes" element={<Pagina titulo="Quartos"><Internacoes /></Pagina>} />
        <Route path="/admin/internacoes/quartos/:numeroQuarto" element={<Pagina titulo="Detalhes do Quarto"><DetalhesQuarto /></Pagina>} />
        <Route path="/admin/atendimentos" element={<Pagina titulo="Atendimentos"><Atendimentos /></Pagina>} />
        <Route
          path="/admin/atendimentos/novo"
          element={<Pagina titulo="Novo Atendimento"><CriarAtendimento /></Pagina>}
        />
        <Route
          path="/admin/consultas/:consultaId"
          element={<Pagina titulo="Detalhes da Consulta"><DetalhesConsulta /></Pagina>}
        />
      </Route>
      <Route element={<MainLayout profissional />}>
        <Route path="/profissional-saude" element={<Pagina titulo="Dashboard"><HomeProfissional /></Pagina>} />
        <Route path="/profissional-saude/perfil" element={<Pagina titulo="Perfil"><PerfilProfissional /></Pagina>} />
        <Route path="/profissional-saude/agenda" element={<Pagina titulo="Agenda"><Agenda /></Pagina>} />
        <Route path="/profissional-saude/nova-consulta" element={<Pagina titulo="Nova Consulta"><NovaConsulta /></Pagina>} />
        <Route path="/profissional-saude/consultas/:consultaId" element={<Pagina titulo="Detalhes da Consulta"><DetalhesConsulta /></Pagina>} />
      </Route>
    </Routes>
  );
}

export default App;