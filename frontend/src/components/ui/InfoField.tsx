interface InfoFieldProps {
  rotulo: string;
  valor: string;
  editando?: boolean;
  onChange?: (valor: string) => void;
  tipo?: "text" | "tel" | "email";
}

function InfoField({ rotulo, valor, editando, onChange, tipo = "text" }: InfoFieldProps) {
  return (
    <div className="flex min-w-0 flex-col gap-2">
      <dt className="text-sm font-semibold text-slate-700">{rotulo}</dt>
      <dd className="min-w-0">
        {editando ? (
          <input
            type={tipo}
            aria-label={rotulo}
            value={valor}
            onChange={(e) => onChange?.(e.target.value)}
            className="w-full rounded-xl border border-principal bg-white px-4 py-3 text-base text-slate-700 outline-none focus:ring-2 focus:ring-destaque"
          />
        ) : (
          <div className="rounded-xl border border-slate-200 bg-slate-50 px-4 py-3 text-base text-slate-700">
            {valor}
          </div>
        )}
      </dd>
    </div>
  );
}

export default InfoField;
