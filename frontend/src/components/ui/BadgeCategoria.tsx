export type CategoriaProfissional = "Clínico Geral" | "Especialista" | "Cirurgião";

const cores: Record<CategoriaProfissional, string> = {
  "Clínico Geral": "border-emerald-200 bg-emerald-50 text-emerald-800",
  Especialista: "border-blue-200 bg-blue-50 text-blue-800",
  Cirurgião: "border-violet-200 bg-violet-50 text-violet-800",
};

function BadgeCategoria({ categoria }: { categoria: CategoriaProfissional }) {
  return (
    <span className={`rounded-full border px-3 py-1 text-sm font-semibold ${cores[categoria]}`}>
      {categoria}
    </span>
  );
}

export default BadgeCategoria;
