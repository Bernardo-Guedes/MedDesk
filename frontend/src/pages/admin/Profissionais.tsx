import ListItem from "@/components/ListItem";
import SearchInput from "@/components/ui/searchInput";
import { UserShield } from "lucide-react";
import { useState } from "react";
import BadgeCategoria, { type CategoriaProfissional } from "@/components/ui/BadgeCategoria";
import SelectFiltro from "@/components/ui/SelectFiltro";

type Profissional =
  | { nome: string; categoria: "Especialista"; especialidade: string }
  | { nome: string; categoria: Exclude<CategoriaProfissional, "Especialista"> };

// Dados de exemplo dos profissionais.
const profissionais: Profissional[] = [
  { nome: "Dr. Carlos Sales", categoria: "Especialista", especialidade: "Pediatria" },
  { nome: "Dr. Roberto Almeida", categoria: "Especialista", especialidade: "Ortopedia" },
  { nome: "Dr. Augusto Faria", categoria: "Cirurgião" },
  { nome: "Dra. Marina Costa", categoria: "Clínico Geral" },
];

const especialidades = [...new Set(
  profissionais.flatMap((profissional) =>
    profissional.categoria === "Especialista" ? [profissional.especialidade] : [],
  ),
)].sort((a, b) => a.localeCompare(b, "pt-BR"));

const normalizar = (texto: string) =>
  texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();

function Profissionais() {
  const [busca, setBusca] = useState("");
  const [categoria, setCategoria] = useState("");
  const [especialidade, setEspecialidade] = useState("");
  const filtrados = profissionais.filter((profissional) =>
    normalizar(profissional.nome).includes(normalizar(busca.trim())) &&
    (!categoria || profissional.categoria === categoria) &&
    (!especialidade ||
      (profissional.categoria === "Especialista" && profissional.especialidade === especialidade)),
  );

  return (
    <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <UserShield size={40} />
        Profissionais
      </div>
      <div className="mt-8">
        <SearchInput value={busca} onChange={setBusca} />
        <div className="mx-10 mb-6 flex flex-wrap gap-4 rounded-2xl border border-slate-200 bg-white p-4 shadow-sm">
          <SelectFiltro
            id="filtro-categoria"
            rotulo="Categoria"
            valor={categoria}
            onChange={setCategoria}
            opcoes={[
              { valor: "", texto: "Todas as categorias" },
              { valor: "Clínico Geral", texto: "Clínico Geral" },
              { valor: "Especialista", texto: "Especialista" },
              { valor: "Cirurgião", texto: "Cirurgião" },
            ]}
          />
          <SelectFiltro
            id="filtro-especialidade"
            rotulo="Especialidade"
            valor={especialidade}
            onChange={setEspecialidade}
            opcoes={[
              { valor: "", texto: "Todas as especialidades" },
              ...especialidades.map((item) => ({ valor: item, texto: item })),
            ]}
          />
        </div>
        {/* CPF de exemplo exibido para os profissionais. */}
        {filtrados.length ? filtrados.map((profissional) => (
          <ListItem
            key={profissional.nome}
            nome={profissional.nome}
            destaque={<BadgeCategoria categoria={profissional.categoria} />}
            detalhes={[
              ...(profissional.categoria === "Especialista" ? [profissional.especialidade] : []),
              "CPF 234.567.890-11",
            ]}
          />
        )) : (
          <p role="status" className="mx-10 text-slate-600">Nenhum profissional encontrado para os filtros selecionados.</p>
        )}
      </div>
    </div>
  );
}

export default Profissionais;
